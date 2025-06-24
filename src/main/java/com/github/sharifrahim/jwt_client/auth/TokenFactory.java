package com.github.sharifrahim.jwt_client.auth;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class TokenFactory {

    private final Map<String, TokenManager> managers;

    public TokenFactory(ProviderXTokenManager providerXTokenManager) {
        this.managers = Map.of("providerX", providerXTokenManager);
    }

    public TokenManager get(String provider) {
        return Optional.ofNullable(managers.get(provider))
                .orElseThrow(() -> new IllegalArgumentException("Unknown provider: " + provider));
    }
}
