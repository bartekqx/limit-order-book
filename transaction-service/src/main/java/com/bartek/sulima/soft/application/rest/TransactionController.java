package com.bartek.sulima.soft.application.rest;

import com.bartek.sulima.soft.domain.TransactionService;
import com.bartek.sulima.soft.domain.dto.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions(HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(transactionService.getTransactionsForUser(getAuthHeader(request)));
    }

    @GetMapping("/series/executed-orders/{interval}")
    public ResponseEntity<List<OrdersSerie>> getExecutedOrders(@PathVariable int interval) {
        return ResponseEntity.ok(transactionService.getExecutedOrders(interval));
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
