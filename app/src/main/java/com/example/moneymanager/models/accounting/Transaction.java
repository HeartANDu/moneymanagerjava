package com.example.moneymanager.models.accounting;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.models.TimestampedEntity;
import com.example.moneymanager.models.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity(name = "transactions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Transaction extends TimestampedEntity {
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false, length = 10)
    private TransactionAction action;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String comment;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    public Transaction(Date date, TransactionAction action, BigDecimal amount, String comment, User user, TransactionType type, Account account) {
        this.date = date;
        this.action = action;
        this.amount = amount;
        this.comment = comment;
        this.user = user;
        this.type = type;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
