package com.bartek.sulima.soft.application.rest.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TokenDto {

    private final String accessToken;
    private final LocalDateTime issueTime;
    private final LocalDateTime expiryTime;

}
