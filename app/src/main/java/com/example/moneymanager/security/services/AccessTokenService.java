package com.example.moneymanager.security.services;

import com.example.moneymanager.security.config.JwtConfig;
import com.example.moneymanager.models.auth.AccessToken;
import com.example.moneymanager.models.auth.User;
import com.example.moneymanager.repository.auth.AccessTokenRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccessTokenService {
    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    private JwtConfig config;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Instant expiresAt = Instant.now().plusMillis(config.getExpirationMs());
        String tokenId = UUID.randomUUID().toString();
        // Maybe not the most elegant solution...
        while (accessTokenRepository.existsById(tokenId)) {
            tokenId = UUID.randomUUID().toString();
        }

        revokeAccessTokens(authentication);

        AccessToken accessToken = new AccessToken(tokenId,
                entityManager.getReference(User.class, userDetails.getId()), false, expiresAt);
        accessTokenRepository.save(accessToken);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(Date.from(expiresAt))
                .signWith(SignatureAlgorithm.HS512, config.getSecret())
                .compact();
    }

    @Transactional
    public String refreshAccessToken(Authentication authentication) {
        revokeAccessTokens(authentication);
        return generateAccessToken(authentication);
    }

    @Transactional
    public void revokeAccessTokens(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        accessTokenRepository.deleteAllByUserId(userDetails.getId());
    }

    public boolean validateJwt(Claims jwt) {
        Optional<AccessToken> token = accessTokenRepository.findById(jwt.getId());
        return token.isPresent() && !token.get().isRevoked();
    }

    public Claims parseJwt(String authToken) {
        try {
            return Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(authToken).getBody();
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return null;
    }
}
