package com.bartek.sulima.soft.application.config.initializer;

import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InstrumentInitializer {

    private final InstrumentRepository instrumentRepository;

    public void initializeInstruments() {
        instrumentRepository.deleteAll();

        InstrumentEntity instrumentEntity1 = InstrumentEntity.builder()
                .code("FX")
                .name("EURUSD")
                .minPrice(BigDecimal.valueOf(1.70))
                .maxPrice(BigDecimal.valueOf(2.15))
                .build();

        InstrumentEntity instrumentEntity2 = InstrumentEntity.builder()
                .code("FX")
                .name("USDJPY")
                .minPrice(BigDecimal.valueOf(2.20))
                .maxPrice(BigDecimal.valueOf(2.90))
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

        InstrumentEntity instrumentEntity6 = InstrumentEntity.builder()
                .code("FX")
                .name("GBPUSD")
                .minPrice(BigDecimal.valueOf(1.25))
                .maxPrice(BigDecimal.valueOf(1.30))
                .build();

        InstrumentEntity instrumentEntity7 = InstrumentEntity.builder()
                .code("FX")
                .name("EURCHF")
                .minPrice(BigDecimal.valueOf(2.45))
                .maxPrice(BigDecimal.valueOf(3.30))
                .build();

        InstrumentEntity instrumentEntity8 = InstrumentEntity.builder()
                .code("FX")
                .name("GBPCHF")
                .minPrice(BigDecimal.valueOf(2.50))
                .maxPrice(BigDecimal.valueOf(3.50))
                .build();

        instrumentRepository.save(instrumentEntity1);
        instrumentRepository.save(instrumentEntity2);
        instrumentRepository.save(instrumentEntity3);
        instrumentRepository.save(instrumentEntity4);
        instrumentRepository.save(instrumentEntity5);
        instrumentRepository.save(instrumentEntity6);
        instrumentRepository.save(instrumentEntity7);
        instrumentRepository.save(instrumentEntity8);

    }
}
