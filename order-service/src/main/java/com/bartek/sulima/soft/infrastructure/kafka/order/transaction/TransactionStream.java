package com.bartek.sulima.soft.infrastructure.kafka.order.transaction;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

interface TransactionStream {

    String OUT_CHANNEL = "transaction-out-channel";

    @Output(OUT_CHANNEL)
    MessageChannel publisher();

}
