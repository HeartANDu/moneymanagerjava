package com.example.moneymanager.controllers;

import com.example.moneymanager.models.accounting.Account;
import com.example.moneymanager.models.accounting.AccountType;
import com.example.moneymanager.models.auth.User;
import com.example.moneymanager.payload.request.CreateOrUpdateAccountRequest;
import com.example.moneymanager.repository.accounting.AccountRepository;
import com.example.moneymanager.repository.accounting.AccountTypeRepository;
import com.example.moneymanager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public List<Account> getAccounts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return accountRepository.findAllByUserId(userDetails.getId());
    }

    @PostMapping
    public Account createAccount(
            @Valid @RequestBody CreateOrUpdateAccountRequest request,
            Authentication authentication
    ) {
        AccountType accountType = accountTypeRepository.findById(request.getAccountTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Account type not found"));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Account account = new Account(request.getName(), accountType,
                entityManager.getReference(User.class, userDetails.getId()), new BigDecimal(0));

        accountRepository.save(account);

        return account;
    }

    @PutMapping("/{id}")
    @PreAuthorize("#account == null || #account.user.id == authentication.principal.id")
    public Account updateAccount(
            @PathVariable(name = "id", required = false) Account account,
            @Valid @RequestBody CreateOrUpdateAccountRequest request
    ) {
        AccountType accountType = accountTypeRepository.findById(request.getAccountTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Account type not found"));

        if (account == null) {
            account = new Account();
        }

        account.setName(request.getName());
        account.setAccountType(accountType);

        accountRepository.save(account);

        return account;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#account != null && #account.user.id == authentication.principal.id")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Account account) {
        accountRepository.delete(account);

        return ResponseEntity.ok().build();
    }
}
