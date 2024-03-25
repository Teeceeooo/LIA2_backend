package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.JwtToken;
import com.lia2.lia2_backend.util.ApplicationConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private ApplicationConfig appConfig;

    public JwtToken generateToken(String username) {
        String jwtToken = Jwts.builder()
                .claim("Username", username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .compact();

        JwtToken jwtTokenObject = new JwtToken();
        jwtTokenObject.setJwtToken(jwtToken);

        return jwtTokenObject;
    }

    public JwtToken generateTokenUsingSecret(String username) {
        String jwtToken = Jwts.builder()
                .claim("Username", username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS256, appConfig.getSecretKey())
                .compact();

        JwtToken jwtTokenObject = new JwtToken();
        jwtTokenObject.setJwtToken(jwtToken);

        return jwtTokenObject;
    }

    public Claims parseJwt(String jwtToken) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(appConfig.getSecretKey())
                .build().parseClaimsJws(jwtToken);
        return jws.getBody();
    }
    public String extractUsername(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes);
            return credentials.split(":", 2)[0];
        }
        return null;
    }
}
