package com.example.moneymanager.security.config;

import com.example.moneymanager.models.auth.Role;
import com.example.moneymanager.repository.auth.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    private void populateRoles() {
        List<Role.Roles> roles = roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        for (Role.Roles baseRole : Role.Roles.values()) {
            if (!roles.contains(baseRole)) {
                roleRepository.save(new Role(baseRole));
            }
        }
    }
}
