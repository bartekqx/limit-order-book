package com.bartek.sulima.soft.application.rest.orders;

import com.bartek.sulima.soft.application.rest.instruments.InstrumentDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;
    private String userId;
    private InstrumentDto instrument;
    private String orderType;
    private BigDecimal price;
    private int quantity;
}
