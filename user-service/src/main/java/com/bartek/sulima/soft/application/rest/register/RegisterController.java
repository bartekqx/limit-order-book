package com.bartek.sulima.soft.application.rest.register;

import com.bartek.sulima.soft.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class RegisterController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateAccountDto createAccountDto) {
        userService.createAccount(createAccountDto);
        return ResponseEntity.ok().build();
    }

}
