package com.revature.BankSystem.Security;

import com.revature.BankSystem.Profile.Profile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(Authentication authentication, int profileId) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(username)
                .claim("profileId", profileId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String getProfileUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // reaches into our token and gets them
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT is expired or incorrect");
        }
    }
}