package com.naher_farhsa.ems.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@AllArgsConstructor
public class JwtUtil {

    private final String SECRET_KEY="myS3cur3K3yWithR@ndomChar@cter$123456789!"; // Secret key for signing and verifying JWTs

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUserName(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50)) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

   /* // Retrieve email (subject) from JWT token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Retrieve a specific claim from JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseSignedClaims(token)
                .getBody();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generate a JWT token for the user
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    // Create a JWT token with claims, subject, and expiration time
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(subject) // Set the subject (email in this case)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token creation date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token expiration time (10 hours)
                .signWith(SignatureAlgorithm.HS256, secretKey) // Sign the token with secret key using HS256 algorithm
                .compact();
    }

    // Validate the token against the email and check if it has expired
    public boolean validateToken(String token, String email) {
        final String username = extractEmail(token);
        return (username.equals(email) && !isTokenExpired(token));
    }*/
}
