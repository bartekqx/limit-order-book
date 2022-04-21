package com.bartek.sulima.soft.application.rest.orders;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSerie {

    private String name;
    private List<OrdersSeriesXYDto> series;
}
