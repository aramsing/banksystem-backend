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

    /**
     * Generates a JWT token from the given authentication object and profileId.
     *
     * The generated token will contain the username as the subject, and the profileId as a claim.
     * The token will be signed with the same key used to sign all other JWT tokens.
     * The expiration date of the token will be set to the current time plus the value of {@link SecurityConstants#JWT_EXPIRATION}.
     * The key is then signed with the {@link SignatureAlgorithm#HS256} algorithm.
     * The builder is then returned to the method as the created token.
     *
     * @param authentication the authentication object to generate the token from
     * @param profileId the profileId to include in the generated token
     * @return the generated JWT token
     */
    public String generateToken(Authentication authentication, int profileId) {
        String username = authentication.getName();
        Date now = new Date(); // used for issue
        Date expiration = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION); // used for expiration
        return Jwts.builder()
                .setSubject(username)
                .claim("profileId", profileId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Gets the username from the given JWT token.
     * @param token the JWT token to extract the username from
     * @return the username if the token is valid, or throws an {@link AuthenticationCredentialsNotFoundException} if the token is invalid
     */
    public String getProfileUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // gets the body of the token with a signed key and parsed token with a builder
        return claims.getSubject(); // returns the subject of the claim
    }

    /**
     * Validates a given JWT token.
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     * @throws AuthenticationCredentialsNotFoundException if the token is invalid or expired
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // gets the body of the token with a signed key and parsed token with a builder if it is valid
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT is expired or incorrect");
        }
    }
}