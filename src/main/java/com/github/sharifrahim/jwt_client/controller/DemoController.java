package com.github.sharifrahim.jwt_client.controller;

/**
 * Simple REST controller that proxies GET requests to another service using tokens.
 *
 * @author sharif rahim
 * @see <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.github.sharifrahim.jwt_client.helper.RestHelper;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    private final RestHelper restHelper;

    public DemoController(RestHelper restHelper) {
        this.restHelper = restHelper;
    }

    /**
     * Calls the provided URL using a bearer token from the given provider.
     */
    @GetMapping("/call")
    public ResponseEntity<String> call(@RequestParam String provider, @RequestParam String url) {
        log.debug("Proxying GET request to {} using provider {}", url, provider);
        return restHelper.get(provider, url);
    }
}
