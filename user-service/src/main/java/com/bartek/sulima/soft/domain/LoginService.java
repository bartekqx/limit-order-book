package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.login.TokensDto;
import com.bartek.sulima.soft.application.rest.login.UserCredentialsDto;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TokenService tokenService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokensDto processUserLogin(UserCredentialsDto dto) {
        final UserEntity userEntity = userService.getByUsername(dto.getUsername());

        if (passwordNotMatch(userEntity.getPassword(), dto.getPassword())) {
            throw new IllegalStateException("Password not match, username = " + dto.getUsername());
        }

        return tokenService.generateTokens(userEntity);
    }

    private boolean passwordNotMatch(String userPassword, String inputPassword) {
        return !passwordEncoder.matches(inputPassword, userPassword);
    }
}
