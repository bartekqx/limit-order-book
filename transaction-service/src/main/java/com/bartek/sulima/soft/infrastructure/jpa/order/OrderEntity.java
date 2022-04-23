package com.bartek.sulima.soft.infrastructure.jpa.order;

import com.bartek.sulima.soft.infrastructure.jpa.transaction.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class OrderEntity {

    @Id
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

    @Column(nullable = false)
    private Instant createTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionEntity transaction;
}
