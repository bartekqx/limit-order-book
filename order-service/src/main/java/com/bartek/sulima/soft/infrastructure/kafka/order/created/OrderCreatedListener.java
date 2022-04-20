package com.bartek.sulima.soft.infrastructure.kafka.order.created;

import com.bartek.sulima.soft.domain.OrderService;
import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class OrderCreatedListener {

    private final OrderService orderService;

    @StreamListener(OrderCreatedStream.IN_CHANNEL)
    public void handleMessage(@Payload OrderEntity createdOrder) {
        log.info("Created order message consumed: " + createdOrder);
        orderService.processCreatedOrder(createdOrder);
    }
}
