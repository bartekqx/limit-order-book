package com.bartek.sulima.soft.application.rest;

import com.bartek.sulima.soft.domain.TransactionService;
import com.bartek.sulima.soft.domain.dto.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*", origins = "*", originPatterns = "*")
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final Sinks.Many<OrdersSeries> ordersSerieSink;

    @GetMapping
    public Flux<TransactionDto> getTransactions(ServerWebExchange exchange) throws JsonProcessingException {
        return transactionService.getTransactionsForUser(getAuthHeader(exchange));
    }

    @GetMapping(value = "/series/executed-orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrdersSeries> getExecutedOrders() {
        return ordersSerieSink.asFlux();
    }

    private String getAuthHeader(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }
}
