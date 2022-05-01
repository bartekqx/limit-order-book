package com.bartek.sulima.soft.domain.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;

    public boolean passwordNotMatch(String userPassword, String inputPassword) {
        return !passwordEncoder.matches(inputPassword, userPassword);
    }
}
