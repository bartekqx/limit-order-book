package com.bartek.sulima.soft.infrastructure.kafka.order.created;

import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedSender {

    private final OrderCreatedStream stream;

    public void send(OrderEntity orderEntity) {
        stream.publisher()
                .send(MessageBuilder.withPayload(orderEntity)
                        .build());
        log.info("Order created message published: " + orderEntity);
    }
}
