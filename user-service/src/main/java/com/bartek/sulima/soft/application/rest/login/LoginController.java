package com.bartek.sulima.soft.application.rest.login;

import com.bartek.sulima.soft.domain.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class LoginController {

    private final LoginService loginService;

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        return ResponseEntity.ok(loginService.processUserLogin(userCredentialsDto));
    }
}
