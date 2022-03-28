package com.bartek.sulima.soft.application.config.security;

import com.bartek.sulima.soft.domain.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;
    private final RouterSecurity routerSecurity;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();

        if (routerSecurity.isEndpointSecured(request)) {
            if (isAuthHeaderMissing(request)) {
                return onError(exchange,"Authorization header is missing!", HttpStatus.UNAUTHORIZED);
            }

            final String token = getAuthHeader(request).replace("Bearer ", "");
            if (jwtUtil.isTokenInvalid(token)) {
                return onError(exchange, "Token is invalid!", HttpStatus.UNAUTHORIZED);
            }
        }

        return chain.filter(exchange);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthHeaderMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, String reason, HttpStatus httpStatus) {
        final ServerHttpResponse response = exchange.getResponse();

        final byte[] bytes = reason.getBytes(StandardCharsets.UTF_8);
        final DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(httpStatus);

        return response.writeWith(Flux.just(buffer));
    }

}
