package app.utility;

import io.jsonwebtoken.*;


import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {
    public static final String JWT_ATTRIBUTE_NAME = "";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADRESS_CLAIM = "ip";

    private String callName;
    private Long userId;
    private String role;

    public JWToken(String callName, Long userId, String role) {
        this.callName = callName;
        this.userId = userId;
        this.role = role;
    }

    public static JWToken decode(String token, String jwtPassphrase)
            throws ExpiredJwtException, MalformedJwtException {
        Key key = getKey(jwtPassphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();

        JWToken jwToken = new JWToken(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );
        jwToken.setIpAdress((String) claims.get(JWT_IPADRESS_CLAIM));
        return jwToken;
    }

    private void setIpAdress(String s) {

    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);
        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, this.callName)
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public String getCallname() {
        return callName;
    }

    public void setCallname(String callName) {
        this.callName = callName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
