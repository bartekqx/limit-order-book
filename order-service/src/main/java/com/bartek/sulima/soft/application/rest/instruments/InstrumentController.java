package com.bartek.sulima.soft.application.rest.instruments;

import com.bartek.sulima.soft.domain.InstrumentService;
import com.bartek.sulima.soft.domain.dto.InstrumentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/instruments")
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService instrumentService;

    @GetMapping
    public Flux<InstrumentDto> getInstruments() {
        return instrumentService.getInstruments();
    }
}
