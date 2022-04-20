package com.bartek.sulima.soft.infrastructure.kafka.order.transaction;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(TransactionStream.class)
class TransactionConfiguration {
}
