package com.bartek.sulima.soft.application.config.initializer;

import com.bartek.sulima.soft.infrastructure.jpa.user.UserEntity;
import com.bartek.sulima.soft.infrastructure.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner cmd() {
        return args -> {
            userRepository.deleteAll();

            UserEntity userEntity = UserEntity.builder()
                    .username("bartek")
                    .password(passwordEncoder.encode("bartek"))
                    .firstName("Bartłomiej")
                    .lastName("Sulima")
                    .phoneNumber(509736776)
                    .email("bartlomiej.sulima@o365.us.edu.pl")
                    .build();

            userRepository.save(userEntity);
        };
    }
}
