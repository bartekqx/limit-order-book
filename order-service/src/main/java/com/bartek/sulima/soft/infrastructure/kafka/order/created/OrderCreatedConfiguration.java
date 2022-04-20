package com.bartek.sulima.soft.infrastructure.kafka.order.created;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(OrderCreatedStream.class)
class OrderCreatedConfiguration {
}
