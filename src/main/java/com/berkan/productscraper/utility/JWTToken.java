package com.berkan.productscraper.utility;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWTToken {

    private static final String JWT_EMAIL_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";

    private Long userId = null;
    private String email = null;

    public JWTToken(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String encode(String issuer, String passPhrase, int expiration) {
        Key key = getKey(passPhrase);

        String token = Jwts.builder()
                .claim(JWT_EMAIL_CLAIM, this.email)
                .claim(JWT_USERID_CLAIM, this.userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return token;
    }

    private static Key getKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }


    public static JWTToken decode(String token, String passphrase) {
        try {
            // Validate the token
            Key key = getKey(passphrase);

            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            Claims claims = jws.getBody();

            return new JWTToken(
                    Long.parseLong(claims.get(JWT_USERID_CLAIM).toString()),
                    claims.get(JWT_USERID_CLAIM).toString()
            );

        } catch (MalformedJwtException |
                UnsupportedJwtException | IllegalArgumentException e) {
            return null;

        }
    }

    public static String getJwtEmailClaim() {
        return JWT_EMAIL_CLAIM;
    }

    public static String getJwtUseridClaim() {
        return JWT_USERID_CLAIM;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
