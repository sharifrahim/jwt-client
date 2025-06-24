package com.github.sharifrahim.jwt_client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.client.RestTemplate;

import com.github.sharifrahim.jwt_client.auth.ProviderXTokenManager;

@ExtendWith(MockitoExtension.class)
class ProviderXTokenManagerTests {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private RestTemplate restTemplate;

    private ProviderXTokenManager manager;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        manager = new ProviderXTokenManager(redisTemplate, restTemplate,
                "client", "secret", "http://provider/token");
    }

    @Test
    void getToken_fetchesNewTokensWhenNoneCached() {
        when(valueOperations.get("providerX:access_token")).thenReturn(null);
        when(valueOperations.get("providerX:refresh_token")).thenReturn(null);

        Map<String, Object> response = Map.of(
                "access_token", "ACCESS",
                "expires_in", 60,
                "refresh_token", "REFRESH",
                "refresh_expires_in", 120
        );
        when(restTemplate.postForObject(eq("http://provider/token"), any(), eq(Map.class)))
                .thenReturn(response);

        String token = manager.getToken();

        assertEquals("ACCESS", token);
        verify(valueOperations).set("providerX:access_token", "ACCESS", Duration.ofSeconds(60));
        verify(valueOperations).set("providerX:refresh_token", "REFRESH", Duration.ofSeconds(120));
    }
}
