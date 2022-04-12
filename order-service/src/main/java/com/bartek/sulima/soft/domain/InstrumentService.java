package com.bartek.sulima.soft.domain;

import com.bartek.sulima.soft.application.rest.instruments.InstrumentDto;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentEntity;
import com.bartek.sulima.soft.infrastructure.jpa.instrument.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public List<InstrumentDto> getInstruments() {
        return instrumentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private InstrumentDto mapToDto(InstrumentEntity entity) {
        return InstrumentDto.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .minPrice(entity.getMinPrice())
                .maxPrice(entity.getMaxPrice())
                .build();
    }

    public InstrumentEntity findByCode(String instrumentCode) {
        return instrumentRepository.findByCode(instrumentCode)
                .orElseThrow(() -> new IllegalStateException("Instrument not found, code=" + instrumentCode));
    }
}
