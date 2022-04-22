package com.bartek.sulima.soft.application.config.sinks;

import com.bartek.sulima.soft.application.config.initializer.InstrumentInitializer;
import com.bartek.sulima.soft.application.rest.orders.OrdersSeries;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class OrdersSerieSinkConfiguration {

    private final InstrumentRepository instrumentRepository;

    @Bean
    public Sinks.Many<OrdersSeries> ordersSerieSink() {
        return Sinks.many().replay().all();
    }

    @Bean
    @ConditionalOnBean(InstrumentInitializer.class)
    public Map<String, AtomicInteger> pendingOrdersCounterMap() {
        return instrumentRepository.findAll()
                .stream()
                .map(InstrumentEntity::getName)
                .collect(Collectors.toMap(instrumentName -> instrumentName, instrumentName -> new AtomicInteger()));
    }
}
