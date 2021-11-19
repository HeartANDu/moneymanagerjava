package com.example.moneymanager.controllers;

import com.example.moneymanager.models.auth.Role;
import com.example.moneymanager.models.auth.User;
import com.example.moneymanager.payload.request.LoginRequest;
import com.example.moneymanager.payload.request.RegisterRequest;
import com.example.moneymanager.payload.response.DataResponseWrapper;
import com.example.moneymanager.payload.response.JwtResponse;
import com.example.moneymanager.payload.response.MessageResponse;
import com.example.moneymanager.payload.response.UserResponse;
import com.example.moneymanager.repository.auth.RoleRepository;
import com.example.moneymanager.repository.auth.UserRepository;
import com.example.moneymanager.security.services.AccessTokenService;
import com.example.moneymanager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AccessTokenService accessTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = accessTokenService.generateAccessToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            roles.add(userRole);
        } else {
            for (String strRole : strRoles) {
                switch (strRole) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Role.Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found"));
                        roles.add(userRole);
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new DataResponseWrapper<>(new UserResponse(userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), userDetails.getRoles())));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(Authentication authentication) {
        String accessToken = accessTokenService.refreshAccessToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Authentication authentication) {
        accessTokenService.revokeAccessTokens(authentication);
        return ResponseEntity.ok(new MessageResponse("Log out successful"));
    }
}
