package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.infrastructure.KeysProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final KeysProvider keysProvider;

    public boolean isTokenInvalid(String token) {
        try {
            parse(token);
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    private Claims parse(String token) {
        return Jwts.parser().setSigningKey(keysProvider.getPublicKey())
                .parseClaimsJws(token).getBody();
    }

}