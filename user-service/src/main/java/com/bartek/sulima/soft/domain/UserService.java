package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.register.CreateAccountDto;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username=" + username + " not found!"));
    }

    public void createAccount(CreateAccountDto createAccountDto) {
        final UserEntity userEntity = UserEntity.builder()
                .password(passwordEncoder.encode(createAccountDto.getPassword()))
                .username(createAccountDto.getUsername())
                .build();

        userRepository.save(userEntity);
    }
}
