package com.bartek.sulima.soft.application.rest.logout;

import com.bartek.sulima.soft.domain.logout.LogoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        final String token = getAuthHeader(httpServletRequest).replace("Bearer ", "");
        logoutService.logout(token);
        return ResponseEntity.ok().build();
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
