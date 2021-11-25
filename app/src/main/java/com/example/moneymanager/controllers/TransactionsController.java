package com.example.moneymanager.controllers;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.accounting.dto.AddNewTransactionDTO;
import com.example.moneymanager.accounting.dto.UpdateTransactionDTO;
import com.example.moneymanager.accounting.services.TransactionsService;
import com.example.moneymanager.models.accounting.Account;
import com.example.moneymanager.models.accounting.Transaction;
import com.example.moneymanager.models.accounting.TransactionType;
import com.example.moneymanager.models.auth.User;
import com.example.moneymanager.payload.request.CreateOrUpdateTransactionRequest;
import com.example.moneymanager.repository.accounting.AccountRepository;
import com.example.moneymanager.repository.accounting.TransactionRepository;
import com.example.moneymanager.repository.accounting.TransactionTypeRepository;
import com.example.moneymanager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/actions")
    public List<TransactionAction> getTransactionActions() {
        return Stream.of(TransactionAction.values())
                .filter(ta -> !ta.isAutomatic())
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<Transaction> getLatestTransactions(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, value = 5) Pageable pageable,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return transactionRepository.findByUserId(userDetails.getId(), pageable);
    }

    @PostMapping
    public Transaction createTransaction(
            @Valid @RequestBody CreateOrUpdateTransactionRequest request,
            Authentication authentication
    ) {
        TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction type not found"));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return transactionsService.addNewTransaction(new AddNewTransactionDTO(request.getDate(),
                request.getAction(), request.getAmount(), request.getComment(),
                entityManager.getReference(User.class, userDetails.getId()), transactionType, account));
    }

    @PutMapping("/{id}")
    @PreAuthorize("#transaction == null || #transaction.user.id == authentication.principal.id")
    public Transaction updateTransaction(
            @PathVariable(name = "id", required = false) Transaction transaction,
            @Valid @RequestBody CreateOrUpdateTransactionRequest request,
            Authentication authentication
    ) {
        if (transaction == null) {
            return createTransaction(request, authentication);
        }

        TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction type not found"));
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return transactionsService.updateTransaction(new UpdateTransactionDTO(transaction, request.getDate(),
                request.getAction(), request.getAmount(), request.getComment(),
                entityManager.getReference(User.class, userDetails.getId()), transactionType, account));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#transaction != null && #transaction.user.id == authentication.principal.id")
    public void deleteTransaction(@PathVariable("id") Transaction transaction) {
        transactionsService.deleteTransaction(transaction);
    }
}
