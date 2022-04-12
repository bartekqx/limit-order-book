package com.bartek.sulima.soft.application.config.initializer;

import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class InstrumentInitializer {

    private final InstrumentRepository instrumentRepository;

    @Bean
    public CommandLineRunner cmd() {
        return args -> {
            InstrumentEntity instrumentEntity1 = InstrumentEntity.builder()
                    .code("FX")
                    .name("USD/EUR")
                    .minPrice(BigDecimal.valueOf(2.75))
                    .maxPrice(BigDecimal.valueOf(3.15))
                    .build();

            InstrumentEntity instrumentEntity2 = InstrumentEntity.builder()
                    .code("STC")
                    .name("CD Project")
                    .minPrice(BigDecimal.valueOf(124.50))
                    .maxPrice(BigDecimal.valueOf(128.75))
                    .build();

            instrumentRepository.save(instrumentEntity1);
            instrumentRepository.save(instrumentEntity2);
        };
    }
}
