package com.bartek.sulima.soft.infrastructure.jpa.transaction;

import com.bartek.sulima.soft.infrastructure.jpa.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction")
public class TransactionEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String instrumentName;

    @Column(nullable = false)
    private Instant createdTime;

    @OneToMany(mappedBy="transaction", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;
}
