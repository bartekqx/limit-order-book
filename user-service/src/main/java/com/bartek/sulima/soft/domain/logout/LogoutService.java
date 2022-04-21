package com.bartek.sulima.soft.domain.logout;

import com.bartek.sulima.soft.domain.token.TokenUtil;
import com.bartek.sulima.soft.infrastructure.jpa.token.TokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService {

    private final TokenRepository tokenRepository;
    private final TokenUtil tokenUtil;

    public void logout(String token) throws JsonProcessingException {
        final String userId = tokenUtil.getUserIdFromToken(token);

        tokenRepository.deleteById(userId);

        log.info("Token revoked for user {} ", userId);
    }
}
