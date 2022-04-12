package com.bartek.sulima.soft.infrastructure.jpa.instrument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instrument")
public class InstrumentEntity {

    @Id
    @Column(nullable = false)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private BigDecimal minPrice;

    @Column(nullable = false, unique = true)
    private BigDecimal maxPrice;

}
