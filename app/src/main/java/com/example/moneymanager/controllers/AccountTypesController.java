package com.example.moneymanager.controllers;

import com.example.moneymanager.models.accounting.AccountType;
import com.example.moneymanager.repository.accounting.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account-types")
public class AccountTypesController {
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping
    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }
}
