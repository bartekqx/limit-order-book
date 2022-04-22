package com.bartek.sulima.soft.application.rest.orders;

import com.bartek.sulima.soft.domain.OrderService;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.OrdersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@RestController
@CrossOrigin(value = "*", origins = "*", originPatterns = "*")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;
    private final Sinks.Many<OrdersSeries> ordersSerieSink;

    @PostMapping
    public Mono<Void> createOrder(ServerWebExchange exchange, @RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderService.createOrder(getAuthHeader(exchange), orderDto);
        return Mono.empty();
    }

    @GetMapping("/{instrumentName}")
    public Mono<OrdersDto> getOrders(@PathVariable String instrumentName) {
        return orderService.getOrdersByInstrumentName(instrumentName);
    }

    @GetMapping(value = "/series/pending-orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrdersSeries> getPendingOrders() {
        return ordersSerieSink.asFlux();
    }

    private String getAuthHeader(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }
}
