package com.bartek.sulima.soft.application.rest.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateAccountDto {

    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int phoneNumber;

}
