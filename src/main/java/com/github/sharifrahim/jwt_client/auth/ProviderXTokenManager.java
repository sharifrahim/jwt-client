package com.github.sharifrahim.jwt_client.auth;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

@Service
public class ProviderXTokenManager implements TokenManager {

    private final StringRedisTemplate redisTemplate;
    private final RestTemplate restTemplate;
    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;

    public ProviderXTokenManager(StringRedisTemplate redisTemplate,
                                 RestTemplate restTemplate,
                                 @Value("${providerX.client-id}") String clientId,
                                 @Value("${providerX.client-secret}") String clientSecret,
                                 @Value("${providerX.token-url}") String tokenUrl) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String getToken() {
        String accessTokenKey = "providerX:access_token";
        String refreshTokenKey = "providerX:refresh_token";
        String token = redisTemplate.opsForValue().get(accessTokenKey);
        if (token != null) {
            return token;
        }
        String refreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
        if (refreshToken != null) {
            return refreshToken(refreshToken, accessTokenKey, refreshTokenKey);
        }
        return fetchNewTokens(accessTokenKey, refreshTokenKey);
    }

    private String fetchNewTokens(String accessTokenKey, String refreshTokenKey) {
        Map<String, String> request = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", "client_credentials");
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(tokenUrl, request, Map.class);
        return storeTokensFromResponse(response, accessTokenKey, refreshTokenKey);
    }

    private String refreshToken(String refreshToken, String accessTokenKey, String refreshTokenKey) {
        Map<String, String> request = Map.of(
                "refresh_token", refreshToken,
                "grant_type", "refresh_token",
                "client_id", clientId,
                "client_secret", clientSecret);
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(tokenUrl, request, Map.class);
        return storeTokensFromResponse(response, accessTokenKey, refreshTokenKey);
    }

    private String storeTokensFromResponse(Map<String, Object> response, String accessTokenKey, String refreshTokenKey) {
        if (response == null) {
            throw new IllegalStateException("No token response from provider");
        }
        String accessToken = (String) response.get("access_token");
        Integer expiresIn = (Integer) response.getOrDefault("expires_in", 3600);
        String refreshToken = (String) response.get("refresh_token");
        Integer refreshExpires = (Integer) response.getOrDefault("refresh_expires_in", 86400);
        if (accessToken != null) {
            redisTemplate.opsForValue().set(accessTokenKey, accessToken, Duration.ofSeconds(expiresIn));
        }
        if (refreshToken != null) {
            redisTemplate.opsForValue().set(refreshTokenKey, refreshToken, Duration.ofSeconds(refreshExpires));
        }
        return accessToken;
    }
}
