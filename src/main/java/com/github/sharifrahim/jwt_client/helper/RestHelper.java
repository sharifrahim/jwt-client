package com.github.sharifrahim.jwt_client.helper;

/**
 * Helper component used for making REST calls with bearer authentication.
 *
 * @author sharif rahim
 * @see <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 */

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import com.github.sharifrahim.jwt_client.auth.TokenFactory;

@Component
@Slf4j
public class RestHelper {

    private final RestTemplate restTemplate;
    private final TokenFactory tokenFactory;

    public RestHelper(RestTemplate restTemplate, TokenFactory tokenFactory) {
        this.restTemplate = restTemplate;
        this.tokenFactory = tokenFactory;
    }

    /**
     * Executes a GET request using a token from the specified provider.
     */
    public ResponseEntity<String> get(String provider, String url) {
        log.debug("Performing GET to {} with provider {}", url, provider);
        String token = tokenFactory.get(provider).getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
