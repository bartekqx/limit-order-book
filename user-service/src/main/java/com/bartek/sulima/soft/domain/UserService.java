package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username=" + username + " not found!"));
    }
}
