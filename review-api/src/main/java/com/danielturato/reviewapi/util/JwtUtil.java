package com.danielturato.reviewapi.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The JWT authentication utility service.
 *
 * Use to generate and validate JWT tokens
 *
 * @author dturato
 */
@Service
public class JwtUtil implements Clock {

    private final static long JWT_DEFAULT_EXPIRY = 900000L; // milliseconds, 15 minutes
    private final static int CLOCK_SKEW = 300; // secs, 5 mins

    private final Environment env;

    @Value("${jwt.issuer}")
    private String issuer;

    public JwtUtil(Environment env) {
        this.env = env;
    }

    /**
     * Create a new temporary JWT token
     * @param attributes The claim attributes to be in the body of the JWT
     * @param subject The subject of the JWT
     * @return The new JWT
     */
    public String createJwtToken(final Map<String, String> attributes, String subject) throws Exception {
        return newToken(attributes, JWT_DEFAULT_EXPIRY, subject);
    }

    /**
     * Generate a new JWT
     * @param attributes The claim attributes to be in the body of the JWT
     * @param expiry The expiry date of the JWT
     * @param subject The subject of the JWT
     * @return The new JWT
     */
    private String newToken(final Map<String, String> attributes, final long expiry, String subject) throws Exception {
        final Date now = now();
        final long expires = now.getTime() + expiry;

        final Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(expires))
                .setSubject(subject);

        claims.putAll(attributes);

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, env.getProperty("JWT_SIGN_KEY"))
                .compact();
    }

    /**
     * Verify a JWT that has been passed through authentication
     * @param token The JWT that needs verifying
     * @return The claims of the JWT - if empty the JWT must be invalid
     */
    public Map<String, String> verify(final String token) throws Exception {
        final JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(CLOCK_SKEW)
                .setSigningKey(env.getProperty("JWT_SIGN_KEY"));

        return parseClaims(() -> parser.parseClaimsJws(token).getBody());
    }

    /**
     * Extract the claims from the body of a JWT
     * @param toClaims The claims of the JWT
     * @return The extracted claims of the JWT
     */
    private Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        try {
            Claims claims = toClaims.get();
            Map<String, String> attributes = new HashMap<>();
            for (Map.Entry<String, Object> e : claims.entrySet()) {
                attributes.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return attributes;
        } catch (IllegalArgumentException | JwtException e) {
            return new HashMap<>();
        }
    }

    @Override
    public Date now() {
        return new Date(System.currentTimeMillis());
    }
}