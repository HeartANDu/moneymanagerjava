package com.example.moneymanager.repository.auth;

import com.example.moneymanager.models.auth.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
    void deleteAllByUserId(Long userId);
}
