package com.bartek.sulima.soft.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;
    private String userId;
    private String instrumentName;
    private String orderType;
    private BigDecimal price;
    private int quantity;
}

