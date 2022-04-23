package com.bartek.sulima.soft.application.rest.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int phoneNumber;
}
