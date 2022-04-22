package com.bartek.sulima.soft.application.rest.orders;

import com.bartek.sulima.soft.domain.OrderService;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.OrdersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;
    private final Sinks.Many<OrdersSerie> ordersSerieSink;

    @PostMapping
    public Mono<Void> createOrder(ServerWebExchange exchange, @RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderService.createOrder(getAuthHeader(exchange), orderDto);
        return Mono.empty();
    }

    @GetMapping("/{instrumentName}")
    public Mono<OrdersDto> getOrders(@PathVariable String instrumentName) {
        return orderService.getOrdersByInstrumentName(instrumentName);
    }

    @GetMapping("/series/pending-orders/{interval}")
    public Flux<OrdersSerie> getPendingOrders(@PathVariable int interval) {
        return orderService.getSeriesForPendingOrders(interval);
    }

    private String getAuthHeader(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }
}
