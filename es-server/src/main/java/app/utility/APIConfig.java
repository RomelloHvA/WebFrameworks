package app.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {
        @Value("${jwt.passphrase}")
        private String jwtPassphrase;

        @Value("${jwt.issuer}")
        private String jwtIssuer;

        @Value("${jwt.expiration-time}")
        private long jwtExpirationTime;

    public String getJwtPassphrase() {
        return jwtPassphrase;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public long getJwtExpirationTime() {
        return jwtExpirationTime;
    }
}
