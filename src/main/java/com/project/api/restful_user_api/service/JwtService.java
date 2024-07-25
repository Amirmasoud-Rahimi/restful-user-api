package com.project.api.restful_user_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Methods to generate, decode, or validate a JSON Web token
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extract Username from JWT token
     *
     * @param token jwt token value
     * @return username
     * @since 1.0.0
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract details from JWT token
     *
     * @param token
     * @param claimsResolver
     * @since 1.0.0
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate JWT token with given information
     *
     * @param userDetails
     * @return JWT token
     * @since 1.0.0
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = getClaimsFromUserDetails(userDetails);
        return buildToken(claims, userDetails, jwtExpiration);
    }

    /**
     * Get tokens expiration date
     *
     * @return expiration date in millisecond
     * @since 1.0.0
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Build JWT token to send as a response to User.
     * To generate the JWT token, we need a secret key and the token expiration time;
     * These values are read from the application configuration properties file using the annotation @Value.
     *
     * @param extraClaims
     * @param userDetails
     * @param expiration
     * @return JWT token
     * @since 1.0.0
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Check token validation
     *
     * @param token
     * @param userDetails
     * @return boolean flag
     * @since 1.0.0
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Check token expiration date
     *
     * @param token
     * @return boolean flag
     * @since 1.0.0
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extract claims from JWT token
     *
     * @param token
     * @return token claims
     * @since 1.0.0
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decode secret key
     *
     * @return Key
     * @since 1.0.0
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract roles from userDetails object to set in JWT token
     *
     * @param userDetails
     * @return user roles
     * @since 1.0.0
     */
    private Map<String, Object> getClaimsFromUserDetails(UserDetails userDetails) {
        List<String> roles = new ArrayList<>();
        userDetails.getAuthorities().forEach(a -> {
            roles.add(a.getAuthority());
        });
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return claims;
    }
}