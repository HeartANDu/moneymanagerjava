package com.example.moneymanager.controllers;

import com.example.moneymanager.models.accounting.TransactionType;
import com.example.moneymanager.models.auth.User;
import com.example.moneymanager.payload.request.CreateOrUpdateTransactionTypeRequest;
import com.example.moneymanager.repository.accounting.TransactionTypeRepository;
import com.example.moneymanager.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction-types")
public class TransactionTypesController {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public List<TransactionType> getTransactionTypes(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return transactionTypeRepository.findAllByUserId(userDetails.getId());
    }

    @PostMapping
    public TransactionType createTransactionType(
            @Valid @RequestBody CreateOrUpdateTransactionTypeRequest request,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        TransactionType transactionType = new TransactionType(request.getName(),
                entityManager.getReference(User.class, userDetails.getId()), request.getAction());

        transactionTypeRepository.save(transactionType);

        return transactionType;
    }

    @PutMapping("/{id}")
    @PreAuthorize("#transactionType == null || #transactionType.user.id == authentication.principal.id")
    public TransactionType updateTransactionType(
            @PathVariable(name = "id", required = false) TransactionType transactionType,
            @Valid @RequestBody CreateOrUpdateTransactionTypeRequest request,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (transactionType == null) {
            transactionType = new TransactionType(entityManager.getReference(User.class, userDetails.getId()));
        }

        transactionType.setName(request.getName());
        transactionType.setAction(request.getAction());

        transactionTypeRepository.save(transactionType);

        return transactionType;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#transactionType != null && #transactionType.user.id == authentication.principal.id")
    public ResponseEntity<?> deleteTransactionType(@PathVariable("id") TransactionType transactionType) {
        transactionTypeRepository.delete(transactionType);

        return ResponseEntity.ok().build();
    }
}
