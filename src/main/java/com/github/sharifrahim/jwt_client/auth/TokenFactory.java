package com.github.sharifrahim.jwt_client.auth;

/**
 * Factory that returns {@link TokenManager} implementations based on a provider name.
 *
 * <p>This class currently supports only ProviderX but can be extended to
 * include more providers.</p>
 *
 * @author sharif rahim
 * @see <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 */

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenFactory {

    private final Map<String, TokenManager> managers;

    public TokenFactory(ProviderXTokenManager providerXTokenManager) {
        this.managers = Map.of("providerX", providerXTokenManager);
    }

    public TokenManager get(String provider) {
        log.debug("Requesting token manager for provider: {}", provider);
        return Optional.ofNullable(managers.get(provider))
                .orElseThrow(() -> new IllegalArgumentException("Unknown provider: " + provider));
    }
}
