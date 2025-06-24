package com.github.sharifrahim.jwt_client;

/**
 * Entry point for the JWT client sample application.
 *
 * @author sharif rahim
 * @see <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class JwtClientApplication {

        public static void main(String[] args) {
                log.info("Starting JWT Client application");
                SpringApplication.run(JwtClientApplication.class, args);
        }

        @Bean
        public RestTemplate restTemplate() {
                return new RestTemplate();
        }

}
