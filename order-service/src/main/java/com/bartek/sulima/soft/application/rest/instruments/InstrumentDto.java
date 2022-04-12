package com.bartek.sulima.soft.application.rest.instruments;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@Builder
public class InstrumentDto {

    private final String code;
    private final String name;
    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
}
