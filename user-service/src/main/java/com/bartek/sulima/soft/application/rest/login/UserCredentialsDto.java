package com.bartek.sulima.soft.application.rest.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCredentialsDto {

    private final String username;
    private final String password;
}
