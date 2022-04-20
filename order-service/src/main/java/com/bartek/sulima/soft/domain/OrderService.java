package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.domain.dto.InstrumentDto;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.model.Order;
import com.bartek.sulima.soft.domain.model.Transaction;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.bartek.sulima.soft.infrastructure.kafka.order.created.OrderCreatedSender;
import com.bartek.sulima.soft.infrastructure.kafka.order.transaction.TransactionSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final InstrumentService instrumentService;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final OrderCreatedSender orderCreatedSender;
    private final TransactionSender transactionSender;

    public void createOrder(String tokenHeader, OrderDto orderDto) throws JsonProcessingException {
        final String token = tokenHeader.replace("Bearer ", "");
        final String userId = getUserId(token);

        final OrderEntity orderEntity = OrderEntity.builder()
                .userId(userId)
                .orderType(orderDto.getOrderType())
                .price(orderDto.getPrice())
                .instrumentName(orderDto.getInstrument().getName())
                .quantity(orderDto.getQuantity())
                .build();

        orderRepository.save(orderEntity);
        orderCreatedSender.send(orderEntity);
    }

    @Transactional
    public void processCreatedOrder(OrderEntity createdOrder) {
        if ("ASK".equalsIgnoreCase(createdOrder.getOrderType())) {
            processAskCreatedOrder(createdOrder);
        } else {
            processBidCreatedOrder(createdOrder);
        }
    }

    private void processBidCreatedOrder(OrderEntity createdOrder) {
        orderRepository.findAskByInstrumentNameAndPriceAndQuantity(
                createdOrder.getInstrumentName(),
                createdOrder.getPrice(),
                createdOrder.getQuantity())
                .stream()
                .min(Comparator.comparing(OrderEntity::getPrice))
                .ifPresent(orderEntity -> processMatchedOrder(createdOrder, orderEntity));
    }

    private void processAskCreatedOrder(OrderEntity createdOrder) {
        orderRepository.findBidByInstrumentNameAndPriceAndQuantity(
                createdOrder.getInstrumentName(),

                createdOrder.getPrice(),
                createdOrder.getQuantity())
                .stream()
                .max(Comparator.comparing(OrderEntity::getPrice))
                .ifPresent(orderEntity -> processMatchedOrder(createdOrder, orderEntity));
    }

    private void processMatchedOrder(OrderEntity createdOrder, OrderEntity orderEntity) {
        final int quantityLeft = orderEntity.getQuantity() - createdOrder.getQuantity();

        if (quantityLeft == 0) {
            orderRepository.delete(orderEntity);
        } else {
            orderEntity.setQuantity(quantityLeft);
            orderRepository.save(orderEntity);
        }

        orderRepository.delete(createdOrder);

        transactionSender.send(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .instrumentName(orderEntity.getInstrumentName())
                .createdTime(Instant.now())
                .orders(List.of(
                        mapToOrder(createdOrder),
                        mapToOrder(orderEntity)
                ))
                .build());
    }

    private Order mapToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .instrumentName(orderEntity.getInstrumentName())
                .price(orderEntity.getPrice())
                .quantity(orderEntity.getQuantity())
                .userId(orderEntity.getUserId())
                .orderType(orderEntity.getOrderType())
                .build();
    }

    public List<OrderDto> getOrdersByInstrumentName(String instrumentName) {
        final InstrumentEntity instrumentEntity = instrumentService.findByName(instrumentName);

        return orderRepository.findByInstrumentName(instrumentName)
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

    private String getUserId(String token) throws JsonProcessingException {
        final String[] chunks = token.split("\\.");
        final String decodedPayloadJson = new String(Base64.getDecoder().decode(chunks[1]));
        final JsonNode jsonNode= objectMapper.readTree(decodedPayloadJson);

        return jsonNode.get("usrId").asText();
    }
}
