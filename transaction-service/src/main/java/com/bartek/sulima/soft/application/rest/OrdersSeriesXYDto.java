package com.bartek.sulima.soft.application.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSeriesXYDto {
    private long name;
    private int value;
}
