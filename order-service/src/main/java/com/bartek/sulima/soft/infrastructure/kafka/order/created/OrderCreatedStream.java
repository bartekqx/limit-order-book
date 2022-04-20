package com.bartek.sulima.soft.infrastructure.kafka.order.created;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

interface OrderCreatedStream {

    String IN_CHANNEL = "order-created-in-channel";
    String OUT_CHANNEL = "order-created-out-channel";

    @Output(OUT_CHANNEL)
    MessageChannel publisher();

    @Input(IN_CHANNEL)
    SubscribableChannel subscriber();
}
