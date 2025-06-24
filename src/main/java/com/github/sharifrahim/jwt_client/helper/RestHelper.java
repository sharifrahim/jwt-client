package com.github.sharifrahim.jwt_client.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.sharifrahim.jwt_client.auth.TokenFactory;

@Component
public class RestHelper {

    private final RestTemplate restTemplate;
    private final TokenFactory tokenFactory;

    public RestHelper(RestTemplate restTemplate, TokenFactory tokenFactory) {
        this.restTemplate = restTemplate;
        this.tokenFactory = tokenFactory;
    }

    public ResponseEntity<String> get(String provider, String url) {
        String token = tokenFactory.get(provider).getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
