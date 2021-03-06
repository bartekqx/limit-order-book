package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.orders.OrdersSeries;
import com.bartek.sulima.soft.domain.dto.InstrumentDto;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.OrdersDto;
import com.bartek.sulima.soft.domain.model.Order;
import com.bartek.sulima.soft.domain.model.Transaction;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.bartek.sulima.soft.infrastructure.kafka.order.created.OrderCreatedSender;
import com.bartek.sulima.soft.infrastructure.kafka.order.transaction.TransactionSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final InstrumentService instrumentService;
    private final OrderRepository orderRepository;
    private final TokenUtil tokenUtil;
    private final OrderCreatedSender orderCreatedSender;
    private final TransactionSender transactionSender;
    private final Sinks.Many<OrdersSeries> ordersSerieSink;
    private final Map<String, AtomicInteger> pendingOrdersCounterMap;

    public void createOrder(String tokenHeader, OrderDto orderDto) throws JsonProcessingException {
        final String userId = tokenUtil.getUserIdFromToken(tokenHeader);

        final OrderEntity orderEntity = OrderEntity.builder()
                .userId(userId)
                .orderType(orderDto.getOrderType())
                .price(orderDto.getPrice())
                .instrumentName(orderDto.getInstrument().getName())
                .quantity(orderDto.getQuantity())
                .createTime(Instant.now())
                .build();

        orderRepository.save(orderEntity);
        orderCreatedSender.send(orderEntity);
        ordersSerieSink.tryEmitNext(OrdersSeries.builder()
                .instrumentName(orderEntity.getInstrumentName())
                .counter(pendingOrdersCounterMap.get(orderEntity.getInstrumentName()).incrementAndGet())
                .timestamp(orderEntity.getCreateTime().toEpochMilli())
                .build());
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
        List<OrderEntity> matchedOrders = orderRepository.findAskByInstrumentNameAndPriceAndQuantity(
                createdOrder.getInstrumentName(),
                createdOrder.getPrice());

        if (!matchedOrders.isEmpty()) {
            processMatchedOrder(createdOrder, matchedOrders);
        }
    }

    private void processAskCreatedOrder(OrderEntity createdOrder) {
        List<OrderEntity> matchedOrders = orderRepository.findBidByInstrumentNameAndPriceAndQuantity(
                createdOrder.getInstrumentName(),
                createdOrder.getPrice());

        if (!matchedOrders.isEmpty()) {
            processMatchedOrder(createdOrder, matchedOrders);
        }
    }

    private void processMatchedOrder(OrderEntity createdOrder, List<OrderEntity> matchedOrder) {
        for (OrderEntity orderEntity : matchedOrder) {
            boolean createdOrderDeleted = process(createdOrder, orderEntity);
            if (createdOrderDeleted) {
                return;
            }
        }
    }

    public boolean process(OrderEntity createdOrder, OrderEntity matchedOrder) {
        int executedOrders;
        boolean createdOrderDeleted = false;

        if (createdOrder.getQuantity() > matchedOrder.getQuantity()) {
            final int quantityLeft = createdOrder.getQuantity() - matchedOrder.getQuantity();
            createdOrder.setQuantity(quantityLeft);
            orderRepository.save(createdOrder);
            orderRepository.delete(matchedOrder);
            executedOrders = 1;

        } else if (createdOrder.getQuantity() < matchedOrder.getQuantity()) {
            final int quantityLeft = matchedOrder.getQuantity() - createdOrder.getQuantity();
            matchedOrder.setQuantity(quantityLeft);
            orderRepository.save(matchedOrder);
            orderRepository.delete(createdOrder);
            executedOrders = 1;
            createdOrderDeleted = true;
        } else {
            orderRepository.delete(matchedOrder);
            orderRepository.delete(createdOrder);
            executedOrders = 2;
            createdOrderDeleted = true;
        }

        transactionSender.send(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .instrumentName(matchedOrder.getInstrumentName())
                .createdTime(Instant.now())
                .orders(List.of(
                        mapToOrder(createdOrder),
                        mapToOrder(matchedOrder)
                ))
                .build());

        for (int i = 0; i < executedOrders; i++) {
            ordersSerieSink.tryEmitNext(OrdersSeries.builder()
                    .instrumentName(matchedOrder.getInstrumentName())
                    .counter(pendingOrdersCounterMap.get(matchedOrder.getInstrumentName()).decrementAndGet())
                    .timestamp(Instant.now().toEpochMilli())
                    .build());
        }

        return createdOrderDeleted;
    }

    private Order mapToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .instrumentName(orderEntity.getInstrumentName())
                .price(orderEntity.getPrice())
                .quantity(orderEntity.getQuantity())
                .userId(orderEntity.getUserId())
                .orderType(orderEntity.getOrderType())
                .createTime(orderEntity.getCreateTime())
                .build();
    }

    public Mono<OrdersDto> getOrdersByInstrumentName(String instrumentName) {
        final InstrumentEntity instrumentEntity = instrumentService.findByName(instrumentName);

        final List<OrderDto> orders = orderRepository.findByInstrumentName(instrumentName)
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

        final List<OrderDto> askOrders = orders.stream()
                .filter(orderDto -> "ASK".equals(orderDto.getOrderType()))
                .collect(Collectors.toList());

        final List<OrderDto> bidOrders = orders.stream()
                .filter(orderDto -> "BID".equals(orderDto.getOrderType()))
                .collect(Collectors.toList());

        return Mono.fromCallable(() -> new OrdersDto(askOrders, bidOrders));
    }
}
