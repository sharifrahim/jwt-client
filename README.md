# JWT Client

This project demonstrates a simple Spring Boot application that obtains OAuth tokens from an external provider and uses them to call other services. Tokens are cached in Redis and refreshed automatically when needed.

## How it works

1. **ProviderXTokenManager** requests an access token and refresh token from the configured provider endpoint. Tokens are cached in Redis with their expiry times.
2. **TokenFactory** returns the appropriate `TokenManager` implementation based on the requested provider name.
3. **RestHelper** obtains a token using the factory and makes authenticated HTTP requests.
4. **DemoController** exposes `/demo/call` which proxies GET requests to another URL using the selected provider's token.

Run the application with Maven:

```bash
./mvnw spring-boot:run
```

Ensure Redis is running and the provider credentials are set in `application.properties`.
