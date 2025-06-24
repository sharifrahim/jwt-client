package com.github.sharifrahim.jwt_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JwtClientApplication {

        public static void main(String[] args) {
                SpringApplication.run(JwtClientApplication.class, args);
        }

        @Bean
        public RestTemplate restTemplate() {
                return new RestTemplate();
        }

}
