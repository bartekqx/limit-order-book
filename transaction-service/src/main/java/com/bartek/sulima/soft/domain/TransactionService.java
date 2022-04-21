package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.OrdersSerie;
import com.bartek.sulima.soft.application.rest.OrdersSeriesXYDto;
import com.bartek.sulima.soft.domain.dto.OrderDto;
import com.bartek.sulima.soft.domain.dto.TransactionDto;
import com.bartek.sulima.soft.domain.model.Order;
import com.bartek.sulima.soft.domain.model.Transaction;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderRepository;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionEntity;
import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;
    private final TokenUtil tokenUtil;

    public List<TransactionDto> getTransactionsForUser(String tokenHeader) throws JsonProcessingException {
        final String userId = tokenUtil.getUserIdFromToken(tokenHeader);

        final List<OrderEntity> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(order -> TransactionDto.builder()
                        .transactionId(order.getTransaction().getId())
                        .order(mapToDto(order))
                        .build())
                .collect(Collectors.toList());
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
    }

    public List<OrdersSerie>  getExecutedOrders(int intervalMinutes) {
        final Instant interval = Instant.now().minus(intervalMinutes, ChronoUnit.MINUTES);
        final List<OrderEntity> orders = orderRepository.findOrdersInInterval(interval);

        final Map<String, List<OrderEntity>> ordersByInstrument = orders.stream().collect(Collectors.groupingBy(OrderEntity::getInstrumentName));
        final List<OrdersSerie> ordersSeries = new ArrayList<>();

        for (Map.Entry<String, List<OrderEntity>> entry : ordersByInstrument.entrySet()) {

            int counter = 0;
            final List<OrdersSeriesXYDto> series = new ArrayList<>();
            for (OrderEntity orderEntity : entry.getValue()) {
                series.add(new OrdersSeriesXYDto(orderEntity.getCreateTime().toEpochMilli(), ++counter));
            }

            final OrdersSerie ordersSerie = OrdersSerie.builder()
                    .name(entry.getKey())
                    .series(series)
                    .build();

            ordersSeries.add(ordersSerie);
        }

        return ordersSeries;
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
