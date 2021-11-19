package com.example.moneymanager.models.auth;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Entity(name = "access_tokens")
@Table(indexes = {@Index(columnList = "user_id")})
@Getter
@Setter
@ToString
public class AccessToken {
    @Id
    @Size(max = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private Instant expiresAt;

    public AccessToken() {
    }

    public AccessToken(String id, User user, boolean revoked, Instant expiresAt) {
        this.id = id;
        this.user = user;
        this.revoked = revoked;
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccessToken that = (AccessToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
