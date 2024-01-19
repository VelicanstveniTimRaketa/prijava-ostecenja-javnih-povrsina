package com.backend.projectapi.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    //private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY") || Dotenv.load().get("JWT_SECRET_KEY");
    private static final String SECRET_KEY = "434bcS3dOk0vx0aoj2udVPFrPsRCXIwQfl/O4MzX+FR23lxB6Xp2BJo8oGzZENKyuH0h4HbT2jTXp4C+oWdhZk0yQ33Ec1VUvIWE4jmtgy3cqeUVxAwyIWFgYLXRMrnmhpqjQX7lcXX9i7q91FWfIhOachLBdVwQgoG415GXDkfws15jEKOtkOMFjnzuKq9YOWAwmvmN91hP6z3T8YZ+XYgukvtPeZG+1w2A3BBCTK86e7ep3d9zJdBIBVrFf5VB0kYpNV3O95BFnxSRsj2aFSrWug61ZB7sC4lQIUjjDU0P5WZ3bvvB2KS7ih9i/TdSMF7Ro/1j/dg6phK4DyDQpGXpFQbQDbuQZ7U3LRaW9IY=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365 * 100)).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Date extractExipration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExipration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
