package com.example.ss6.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;


@ConfigurationProperties(prefix = "spring.security.config")
@Data
@Validated

public class SpringSecurityConfigProperties {

    @NonNull
    private final String signingKey;

    @NonNull
    private final Duration refreshTokenValidity;

    @NonNull
    private final Duration accessTokenValidity;

    @NonNull
    private final Duration resetTokenValidity;
}
