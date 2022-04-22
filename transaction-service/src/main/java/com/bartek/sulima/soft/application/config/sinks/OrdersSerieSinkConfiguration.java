package com.bartek.sulima.soft.application.config.sinks;

import com.bartek.sulima.soft.application.rest.OrdersSeries;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
@RequiredArgsConstructor
public class OrdersSerieSinkConfiguration {

    @Bean
    public Sinks.Many<OrdersSeries> ordersSerieSink() {
        return Sinks.many().replay().all();
    }

}
