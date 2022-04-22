package com.bartek.sulima.soft.application.config.initializer;

import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InstrumentInitializer {

    private final InstrumentRepository instrumentRepository;

    @PostConstruct
    public void postConstruct() {
        InstrumentEntity instrumentEntity1 = InstrumentEntity.builder()
                .code("FX")
                .name("EURUSD")
                .minPrice(BigDecimal.valueOf(1.70))
                .maxPrice(BigDecimal.valueOf(2.15))
                .build();

        InstrumentEntity instrumentEntity2 = InstrumentEntity.builder()
                .code("STC")
                .name("CD Project")
                .minPrice(BigDecimal.valueOf(154.50))
                .maxPrice(BigDecimal.valueOf(168.75))
                .build();

        InstrumentEntity instrumentEntity3 = InstrumentEntity.builder()
                .code("FX")
                .name("USDCAD")
                .minPrice(BigDecimal.valueOf(2.70))
                .maxPrice(BigDecimal.valueOf(3.10))
                .build();

        InstrumentEntity instrumentEntity4 = InstrumentEntity.builder()
                .code("FX")
                .name("GPBEUR")
                .minPrice(BigDecimal.valueOf(1.05))
                .maxPrice(BigDecimal.valueOf(2.20))
                .build();

        InstrumentEntity instrumentEntity5 = InstrumentEntity.builder()
                .code("FX")
                .name("AUDUSD")
                .minPrice(BigDecimal.valueOf(0.55))
                .maxPrice(BigDecimal.valueOf(0.75))
                .build();

        instrumentRepository.save(instrumentEntity1);
        instrumentRepository.save(instrumentEntity2);
        instrumentRepository.save(instrumentEntity3);
        instrumentRepository.save(instrumentEntity4);
        instrumentRepository.save(instrumentEntity5);
    }
}
