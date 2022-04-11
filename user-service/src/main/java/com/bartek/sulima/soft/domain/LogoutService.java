package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.infrastructure.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final UserRepository userRepository;

    public void logout(String token) {

    }
}
