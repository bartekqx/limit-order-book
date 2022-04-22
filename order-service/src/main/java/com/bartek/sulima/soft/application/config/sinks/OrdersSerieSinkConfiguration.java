package com.bartek.sulima.soft.application.config.sinks;

import com.bartek.sulima.soft.application.rest.orders.OrdersSerie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class OrdersSerieSinkConfiguration {

    @Bean
    public Sinks.Many<OrdersSerie> ordersSerieSink() {
        return Sinks.many().replay().all();
    }
}
