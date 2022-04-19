package com.bartek.sulima.soft.infrastructure.jpa.order;

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
@Table(name="\"order\"")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String instrumentName;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String orderType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;
}
