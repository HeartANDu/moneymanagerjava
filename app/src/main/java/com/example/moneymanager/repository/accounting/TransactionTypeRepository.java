package com.example.moneymanager.repository.accounting;

import com.example.moneymanager.models.accounting.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    List<TransactionType> findAllByUserId(Long userId);
}