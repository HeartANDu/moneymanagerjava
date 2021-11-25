package com.example.moneymanager.accounting.services;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.accounting.dto.AddNewTransactionDTO;
import com.example.moneymanager.accounting.dto.UpdateTransactionDTO;
import com.example.moneymanager.models.accounting.Account;
import com.example.moneymanager.models.accounting.Transaction;
import com.example.moneymanager.repository.accounting.AccountRepository;
import com.example.moneymanager.repository.accounting.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionsService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Transaction addNewTransaction(AddNewTransactionDTO dto) {
        Account account = dto.getAccount();
        TransactionAction action = dto.getAction();
        BigDecimal amount = getResultingAmount(action, dto.getAmount());

        Transaction transaction = new Transaction(dto.getDate(), action, amount, dto.getComment(), dto.getUser(),
                dto.getType(), account);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        return transaction;
    }

    @Transactional
    public Transaction updateTransaction(UpdateTransactionDTO dto) {
        Account targetAccount = dto.getAccount();
        TransactionAction action = dto.getAction();
        BigDecimal targetAmount = getResultingAmount(action, dto.getAmount());

        Transaction currentTransaction = dto.getCurrentTransaction();
        Account currentAccount = currentTransaction.getAccount();
        BigDecimal currentAmount = currentTransaction.getAmount();

        currentTransaction.setDate(dto.getDate());
        currentTransaction.setAction(dto.getAction());
        currentTransaction.setAmount(targetAmount);
        currentTransaction.setComment(dto.getComment());
        currentTransaction.setUser(dto.getUser());
        currentTransaction.setType(dto.getType());
        currentTransaction.setAccount(targetAccount);
        transactionRepository.save(currentTransaction);

        currentAccount.setBalance(currentAccount.getBalance().subtract(currentAmount));
        targetAccount.setBalance(targetAccount.getBalance().add(targetAmount));

        accountRepository.save(currentAccount);
        if (!targetAccount.equals(currentAccount)) {
            accountRepository.save(targetAccount);
        }

        return currentTransaction;
    }

    @Transactional
    public void deleteTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        account.setBalance(account.getBalance().subtract(transaction.getAmount()));

        accountRepository.save(account);
        transactionRepository.delete(transaction);
    }

    private BigDecimal getResultingAmount(TransactionAction action, BigDecimal amount) {
        if (action == TransactionAction.SUB) {
            return amount.multiply(new BigDecimal(-1));
        }

        return amount;
    }
}
