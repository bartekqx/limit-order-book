package com.bartek.sulima.soft.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private String instrumentName;
    private String userId;
    private String orderType;
    private BigDecimal price;
    private int quantity;
}
