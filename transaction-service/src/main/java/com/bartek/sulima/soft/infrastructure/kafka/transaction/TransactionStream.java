package com.bartek.sulima.soft.infrastructure.kafka.transaction;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

interface TransactionStream {

    String IN_CHANNEL = "transaction-in-channel";

    @Input(IN_CHANNEL)
    SubscribableChannel subscriber();

}
