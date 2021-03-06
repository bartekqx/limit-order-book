package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.domain.dto.InstrumentDto;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public Flux<InstrumentDto> getInstruments() {
        return Flux.fromIterable(instrumentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList()));
    }

    private InstrumentDto mapToDto(InstrumentEntity entity) {
        return InstrumentDto.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .minPrice(entity.getMinPrice())
                .maxPrice(entity.getMaxPrice())
                .build();
    }

    public InstrumentEntity findByName(String name) {
        return instrumentRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Instrument not found, name=" + name));
    }
}
