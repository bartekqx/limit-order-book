package com.bartek.sulima.soft.application.rest.orders;

import com.bartek.sulima.soft.domain.OrderService;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.OrdersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{instrumentName}")
    public ResponseEntity<OrdersDto> getOrders(@PathVariable String instrumentName) {
        return ResponseEntity.ok(orderService.getOrdersByInstrumentName(instrumentName));
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
