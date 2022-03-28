package com.bartek.sulima.soft.application.config.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RouterSecurity {

    private static final List<String> NOT_SECURED_ENDPOINTS = Arrays.asList(
            "/sign-up",
            "/sign-in"
    );

    public boolean isEndpointSecured(ServerHttpRequest request) {
        return NOT_SECURED_ENDPOINTS
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
