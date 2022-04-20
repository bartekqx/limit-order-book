package com.bartek.sulima.soft.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class Transaction {

    private String transactionId;
    private String instrumentName;
    private Instant createdTime;
    private List<Order> orders;
}
