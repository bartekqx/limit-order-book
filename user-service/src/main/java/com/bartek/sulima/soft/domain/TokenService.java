package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.login.TokensDto;
import com.bartek.sulima.soft.infrastructure.keys.KeysProvider;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final KeysProvider jwtKeyProvider;

    public TokensDto generateTokens(UserEntity userEntity) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiryTime = now.plusSeconds(15);

        final String accessToken = Jwts.builder()
                .setClaims(null)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(DateUtil.toDate(now))
                .setExpiration(DateUtil.toDate(expiryTime))
                .signWith(SignatureAlgorithm.RS256, jwtKeyProvider.getPrivateKey())
                .compact();

        return new TokensDto(accessToken);
    }
}
