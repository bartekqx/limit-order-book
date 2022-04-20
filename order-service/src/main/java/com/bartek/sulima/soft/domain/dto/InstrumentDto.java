package com.bartek.sulima.soft.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentDto {

    private String code;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
