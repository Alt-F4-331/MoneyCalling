package com.example.moneycalling_spring.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // Cheia secretă pentru semnarea token-urilor (ar trebui să fie într-o variabilă de mediu)
    private final String SECRET_KEY = "secret-key-for-jwt-should-be-very-long-and-secure";

    // Obiect Key derivat din cheia secretă
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Metodă pentru generarea unui token
    public String generateToken(int userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Valabil 10 ore
                .signWith(key)
                .compact();
    }

    // Metodă pentru extragerea ID-ului utilizatorului din token
    public int extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    // Metodă pentru validarea token-ului
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("Token valid!");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token invalid! Detalii: " + e.getMessage());
            return false;
        }
    }
}
