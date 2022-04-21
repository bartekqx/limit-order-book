package com.bartek.sulima.soft.application.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSerie {

    private String name;
    private List<OrdersSeriesXYDto> series;
}
