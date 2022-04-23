package com.bartek.sulima.soft.application.config.sinks;

import com.bartek.sulima.soft.application.config.initializer.InstrumentInitializer;
import com.bartek.sulima.soft.application.rest.orders.OrdersSeries;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrdersSerieSinkConfiguration {

    private final InstrumentRepository instrumentRepository;

    @Bean
    public Sinks.Many<OrdersSeries> ordersSerieSink() {
        return Sinks.many().replay().all();
    }

    @Bean
    public Map<String, AtomicInteger> pendingOrdersCounterMap(InstrumentInitializer instrumentInitializer) {
        instrumentInitializer.initializeInstruments();
        return instrumentRepository.findAll()
                .stream()
                .map(InstrumentEntity::getName)
                .collect(Collectors.toMap(instrumentName -> instrumentName, instrumentName -> new AtomicInteger()));
    }
}
