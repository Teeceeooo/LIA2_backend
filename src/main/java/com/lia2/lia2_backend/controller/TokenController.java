package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.JwtToken;
import com.lia2.lia2_backend.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("api/v1/token")
@CrossOrigin("*")
public class TokenController {

    @Autowired
    private TokenService tokenService;
    @PostMapping("/generate")
    public ResponseEntity<JwtToken> generateJwtToken(@RequestHeader("Authorization") String authorizationHeader) {
        String username = tokenService.extractUsername(authorizationHeader);

        return new ResponseEntity<>(tokenService.generateTokenUsingSecret(username), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public Claims jwtTokenValidate(@RequestHeader(value = "token", required = true) String jwtToken) {
        return tokenService.parseJwt(jwtToken);
    }

}
