package com.bartek.sulima.soft.application.rest.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username) {
        return ResponseEntity.ok("Dummy user!");
    }
}
