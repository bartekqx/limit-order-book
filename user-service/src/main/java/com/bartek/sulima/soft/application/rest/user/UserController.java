package com.bartek.sulima.soft.application.rest.user;

import com.bartek.sulima.soft.domain.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/details")
    public ResponseEntity<UserDto> getUserDetails(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(userService.findUser(getAuthHeader(request)));
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
