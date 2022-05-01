package com.bartek.sulima.soft.domain.login;

import com.bartek.sulima.soft.application.rest.login.TokenDto;
import com.bartek.sulima.soft.application.rest.login.UserCredentialsDto;
import com.bartek.sulima.soft.domain.PasswordNotMatchException;
import com.bartek.sulima.soft.domain.password.PasswordService;
import com.bartek.sulima.soft.domain.token.TokenService;
import com.bartek.sulima.soft.domain.user.UserService;
import com.bartek.sulima.soft.infrastructure.jpa.token.TokenRepository;
import com.bartek.sulima.soft.infrastructure.jpa.token.TokenEntity;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordService passwordService;
    private final TokenRepository tokenRepository;

    public TokenDto processUserLogin(UserCredentialsDto dto) {
        final UserEntity userEntity = userService.getByUsername(dto.getUsername());

        if (passwordService.passwordNotMatch(userEntity.getPassword(), dto.getPassword())) {
            throw new PasswordNotMatchException("Password not match, username = " + dto.getUsername());
        }

        final TokenDto tokenDto = tokenService.generateToken(userEntity);
        final TokenEntity tokenEntity = TokenEntity.builder()
                .userId(userEntity.getUserId())
                .issueTime(tokenDto.getIssueTime())
                .expirationTime(tokenDto.getExpiryTime())
                .token(tokenDto.getAccessToken())
                .build();

        tokenRepository.save(tokenEntity);
        log.info("Access token generated, userId={}, token={}", userEntity.getUserId(), tokenDto.getAccessToken());

        return tokenDto;
    }

}
