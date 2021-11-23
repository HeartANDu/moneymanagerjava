package com.example.moneymanager.repository.accounting;

import com.example.moneymanager.models.accounting.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
