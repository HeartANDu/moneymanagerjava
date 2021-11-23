package com.example.moneymanager.models.accounting;

import com.example.moneymanager.accounting.TransactionAction;
import com.example.moneymanager.models.auth.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name = "transaction_types")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private User user;

    @Column(nullable = false, length = 10)
    private TransactionAction action;

    public TransactionType(String name, User user, TransactionAction action) {
        this.name = name;
        this.user = user;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TransactionType that = (TransactionType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
