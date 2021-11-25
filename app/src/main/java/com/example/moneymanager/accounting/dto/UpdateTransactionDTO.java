package com.example.moneymanager.accounting.dto;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.models.accounting.Account;
import com.example.moneymanager.models.accounting.Transaction;
import com.example.moneymanager.models.accounting.TransactionType;
import com.example.moneymanager.models.auth.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateTransactionDTO {
    private final Transaction currentTransaction;
    private final Date date;
    private final TransactionAction action;
    private final BigDecimal amount;
    private final String comment;
    private final User user;
    private final TransactionType type;
    private final Account account;
}
