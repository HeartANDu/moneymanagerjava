package com.example.moneymanager.models.accounting;

import com.example.moneymanager.models.TimestampedEntity;
import com.example.moneymanager.models.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "accounts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Account extends TimestampedEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonProperty("account_type")
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, precision = 2)
    private BigDecimal balance;

    public Account(User user) {
        this.user = user;
    }

    public Account(String name, AccountType accountType, User user, BigDecimal balance) {
        this.name = name;
        this.accountType = accountType;
        this.user = user;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
