package com.bartek.sulima.soft.application.rest.register;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    @PostMapping("/sign-up")
    public String signUp() {
        return "Hello dummy!";
    }

}
