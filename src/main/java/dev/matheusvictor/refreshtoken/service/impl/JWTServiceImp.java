package dev.matheusvictor.refreshtoken.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImp {

    private String generateToken(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expirationDateOneDay = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        return Jwts.builder().setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDateOneDay)
                .signWith(getSecretKey(), getSignatureAlgorithm())
                .compact();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Key getSecretKey() {
        return Keys.secretKeyFor(getSignatureAlgorithm());
    }

    private SignatureAlgorithm getSignatureAlgorithm() {
        return SignatureAlgorithm.HS256;
    }
}
