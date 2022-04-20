package com.bartek.sulima.soft.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Order {

    private Long id;
    private String instrumentName;
    private String userId;
    private String orderType;
    private BigDecimal price;
    private int quantity;
}
