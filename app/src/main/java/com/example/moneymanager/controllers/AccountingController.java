package com.example.moneymanager.controllers;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.models.accounting.Transaction;
import com.example.moneymanager.repository.accounting.TransactionRepository;
import com.example.moneymanager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/accounting")
public class AccountingController {
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/actions")
    public List<TransactionAction> getTransactionActions() {
        return Stream.of(TransactionAction.values())
                .filter(ta -> !ta.isAutomatic())
                .collect(Collectors.toList());
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getLatestTransactions(Pageable pageable, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Page<Transaction> transactionPage = transactionRepository.findByUserId(userDetails.getId(), pageable);
        // TODO send sort params from front: Sort.by(Sort.Order.desc("date"), Sort.Order.desc("updatedAt"))

        List<Transaction> transactions = transactionPage.getContent();

        return ResponseEntity.ok(transactions);
    }
}
