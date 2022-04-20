package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.domain.model.Order;
import com.bartek.sulima.soft.domain.model.Transaction;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionEntity;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;

    public void save(Transaction transaction) {
        final TransactionEntity transactionEntity = mapToEntity(transaction);

        transactionRepository.save(transactionEntity);

        final List<OrderEntity> orderEntities = transaction.getOrders()
                .stream()
                .map(order -> mapToEntity(order, transactionEntity))
                .collect(Collectors.toList());

        orderEntities.forEach(orderRepository::save);

        log.info("Transaction saved successfully, {} ", transactionEntity);
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
