package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.instruments.InstrumentDto;
import com.bartek.sulima.soft.application.rest.orders.OrderDto;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final InstrumentService instrumentService;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public void createOrder(String tokenHeader, OrderDto orderDto) throws JsonProcessingException {
        final String token = tokenHeader.replace("Bearer ", "");
        final String username = getUsernameFromToken(token);

        final OrderEntity orderEntity = OrderEntity.builder()
                .userId(username)
                .orderType(orderDto.getOrderType())
                .price(orderDto.getPrice())
                .instrumentCode(orderDto.getInstrument().getCode())
                .quantity(orderDto.getQuantity())
                .build();

        orderRepository.save(orderEntity);
    }

    private String getUsernameFromToken(String token) throws JsonProcessingException {
        final String[] chunks = token.split("\\.");
        final String decodedPayloadJson = new String(Base64.getDecoder().decode(chunks[1]));
        final JsonNode jsonNode= objectMapper.readTree(decodedPayloadJson);

        return jsonNode.get("sub").asText();
    }

    public List<OrderDto> getOrdersByCode(String instrumentCode) {
        final InstrumentEntity instrumentEntity = instrumentService.findByCode(instrumentCode);

        return orderRepository.findByInstrumentCode(instrumentCode)
                .stream()
                .map(orderEntity -> {
                    return OrderDto.builder()
                            .instrument(InstrumentDto.builder()
                                    .code(instrumentEntity.getCode())
                                    .name(instrumentEntity.getName())
                                    .minPrice(instrumentEntity.getMinPrice())
                                    .maxPrice(instrumentEntity.getMaxPrice())
                                    .build())
                            .id(orderEntity.getId())
                            .orderType(orderEntity.getOrderType())
                            .price(orderEntity.getPrice())
                            .quantity(orderEntity.getQuantity())
                            .userId(orderEntity.getUserId())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
