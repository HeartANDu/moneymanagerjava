package com.example.moneymanager.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moneymanager.app.cors")
@Getter
@Setter
public class CorsConfig {
    private String allowedOrigin;
}
