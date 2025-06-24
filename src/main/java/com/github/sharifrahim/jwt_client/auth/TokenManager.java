package com.github.sharifrahim.jwt_client.auth;

/**
 * Contract for components able to obtain access tokens.
 *
 * @author sharif rahim
 * @see <a href="https://github.com/sharifrahim">https://github.com/sharifrahim</a>
 */

public interface TokenManager {
    String getToken();
}
