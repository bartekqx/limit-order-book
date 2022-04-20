package com.bartek.sulima.soft.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersDto {

    private List<OrderDto> askOrders;

    private List<OrderDto> bidOrders;
}
