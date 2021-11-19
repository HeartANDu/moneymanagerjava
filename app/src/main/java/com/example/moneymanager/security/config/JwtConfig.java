package com.example.moneymanager.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moneymanager.app.jwt")
@Getter
@Setter
public class JwtConfig {
    private String secret;
    private int expirationMs;
}
