package com.example.moneymanager.seeders.accounting;

import com.example.moneymanager.models.accounting.AccountType;
import com.example.moneymanager.repository.accounting.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountTypeSeeder {
    @Autowired
    private AccountTypeRepository repository;

    @PostConstruct
    private void seedAccountTypes() {
        Set<String> accountTypes = repository.findAll().stream()
                .map(AccountType::getName)
                .collect(Collectors.toSet());

        for (Type type : Type.values()) {
            if (!accountTypes.contains(type.getName())) {
                repository.save(new AccountType(type.getName()));
            }
        }
    }

    private enum Type {
        CASH("Cash"),
        DEBIT("Debit"),
        CREDIT("Credit");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
