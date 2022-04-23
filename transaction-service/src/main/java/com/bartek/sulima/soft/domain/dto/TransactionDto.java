package com.bartek.sulima.soft.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionDto {

    private String transactionId;
    private long createdTime;
    private OrderDto order;
}
