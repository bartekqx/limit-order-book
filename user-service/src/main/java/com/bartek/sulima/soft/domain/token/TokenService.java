package com.bartek.sulima.soft.domain.token;

import com.bartek.sulima.soft.application.rest.login.TokenDto;
import com.bartek.sulima.soft.domain.DateUtil;
import com.bartek.sulima.soft.infrastructure.keys.KeysProvider;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final KeysProvider jwtKeyProvider;

    public TokenDto generateToken(UserEntity userEntity) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiryTime = now.plusHours(24);

        final Map<String, Object> claims = new HashMap<>();
        claims.put("usrId", userEntity.getUserId());
        claims.put("firstName", userEntity.getFirstName());
        claims.put("lastName", userEntity.getLastName());

        final String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(DateUtil.toDate(now))
                .setExpiration(DateUtil.toDate(expiryTime))
                .signWith(SignatureAlgorithm.RS256, jwtKeyProvider.getPrivateKey())
                .compact();

        return new TokenDto(accessToken, now, expiryTime);
    }
}
