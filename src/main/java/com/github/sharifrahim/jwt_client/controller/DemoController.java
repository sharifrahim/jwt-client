package com.github.sharifrahim.jwt_client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.sharifrahim.jwt_client.helper.RestHelper;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final RestHelper restHelper;

    public DemoController(RestHelper restHelper) {
        this.restHelper = restHelper;
    }

    @GetMapping("/call")
    public ResponseEntity<String> call(@RequestParam String provider, @RequestParam String url) {
        return restHelper.get(provider, url);
    }
}
