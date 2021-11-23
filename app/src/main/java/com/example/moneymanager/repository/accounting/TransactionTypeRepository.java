package com.example.moneymanager.repository.accounting;

import com.example.moneymanager.models.accounting.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
}