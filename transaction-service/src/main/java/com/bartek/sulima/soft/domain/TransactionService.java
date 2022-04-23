package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.OrdersSeries;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.TransactionDto;
import com.bartek.sulima.soft.domain.model.Order;
import com.bartek.sulima.soft.domain.model.Transaction;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionEntity;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;
    private final TokenUtil tokenUtil;
    private final ConcurrentHashMap<String, AtomicInteger> executedOrdersCounter;
    private final Sinks.Many<OrdersSeries> ordersSerieSink;

    public TransactionService(TransactionRepository transactionRepository,
                              OrderRepository orderRepository,
                              TokenUtil tokenUtil,
                              Sinks.Many<OrdersSeries> ordersSerieSink) {
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.tokenUtil = tokenUtil;
        this.ordersSerieSink = ordersSerieSink;
        executedOrdersCounter = new ConcurrentHashMap<>();
    }

    public Flux<TransactionDto> getTransactionsForUser(String tokenHeader) throws JsonProcessingException {
        final String userId = tokenUtil.getUserIdFromToken(tokenHeader);

        final List<OrderEntity> orders = orderRepository.findAllByUserId(userId);

        return Flux.fromIterable(orders.stream()
                .map(order -> TransactionDto.builder()
                        .transactionId(order.getTransaction().getId())
                        .order(mapToDto(order))
                        .createdTime(order.getTransaction().getCreatedTime().toEpochMilli())
                        .build())
                .collect(Collectors.toList()));
    }

    public void save(Transaction transaction) {
        final TransactionEntity transactionEntity = mapToEntity(transaction);

        transactionRepository.save(transactionEntity);

        final List<OrderEntity> orderEntities = transaction.getOrders()
                .stream()
                .map(order -> mapToEntity(order, transactionEntity))
                .collect(Collectors.toList());

        orderEntities.forEach(orderRepository::save);

        log.info("Transaction saved successfully, {} ", transactionEntity);

        final AtomicInteger counter = executedOrdersCounter.getOrDefault(transactionEntity.getInstrumentName(), new AtomicInteger());
        counter.incrementAndGet();
        executedOrdersCounter.put(transactionEntity.getInstrumentName(), counter);

        ordersSerieSink.tryEmitNext(OrdersSeries.builder()
                .instrumentName(transactionEntity.getInstrumentName())
                .counter(counter.get())
                .timestamp(transaction.getCreatedTime().toEpochMilli())
                .build());

    }


    private OrderDto mapToDto(OrderEntity order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .orderType(order.getOrderType())
                .instrumentName(order.getInstrumentName())
                .userId(order.getUserId())
                .build();
    }

    private OrderEntity mapToEntity(Order order, TransactionEntity entity) {
        return OrderEntity.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .orderType(order.getOrderType())
                .instrumentName(order.getInstrumentName())
                .userId(order.getUserId())
                .transaction(entity)
                .createTime(order.getCreateTime())
                .build();
    }

    private TransactionEntity mapToEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .instrumentName(transaction.getInstrumentName())
                .id(transaction.getTransactionId())
                .createdTime(transaction.getCreatedTime())
                .build();
    }
}
