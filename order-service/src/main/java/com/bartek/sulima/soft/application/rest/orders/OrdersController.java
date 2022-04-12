package com.bartek.sulima.soft.application.rest.orders;

import com.bartek.sulima.soft.domain.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderService.createOrder(getAuthHeader(request), orderDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{instrumentCode}")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String instrumentCode) {
        return ResponseEntity.ok(orderService.getOrdersByCode(instrumentCode));
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
