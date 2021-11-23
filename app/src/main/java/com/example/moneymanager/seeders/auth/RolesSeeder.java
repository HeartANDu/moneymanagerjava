package com.example.moneymanager.seeders.auth;

import com.example.moneymanager.models.auth.Role;
import com.example.moneymanager.repository.auth.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolesSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    private void seedRoles() {
        Set<Role.Roles> roles = roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        for (Role.Roles baseRole : Role.Roles.values()) {
            if (!roles.contains(baseRole)) {
                roleRepository.save(new Role(baseRole));
            }
        }
    }
}
