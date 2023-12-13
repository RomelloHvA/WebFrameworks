package app.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class APIConfig {
        @Value("${jwt.passphrase}")
        private String jwtPassphrase;

        @Value("${jwt.issuer}")
        private String jwtIssuer;

        @Value("${jwt.expiration-time}")
        private long jwtExpirationTime;

        @Value("${jwt.secured-paths}")
        protected List<String> SECURED_PATHS;

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
