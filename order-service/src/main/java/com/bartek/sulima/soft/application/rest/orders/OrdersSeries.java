package com.bartek.sulima.soft.application.rest.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSeries {

    private String instrumentName;
    private long timestamp;
    private int counter;
}
