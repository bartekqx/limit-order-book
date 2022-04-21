package com.bartek.sulima.soft.infrastructure.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.createTime >= ?1 order by o.createTime")
    List<OrderEntity> findOrdersInInterval(Instant createTime);

    List<OrderEntity> findByInstrumentName(String instrumentName);

    @Query("SELECT o FROM OrderEntity o WHERE o.instrumentName = ?1 and o.orderType='BID' and o.price >= ?2 and o.quantity >= ?3")
    List<OrderEntity> findBidByInstrumentNameAndPriceAndQuantity(String instrumentName, BigDecimal price, int quantity);

    @Query("SELECT o FROM OrderEntity o WHERE o.instrumentName = ?1 and o.orderType='ASK' and o.price <= ?2 and o.quantity >= ?3")
    List<OrderEntity> findAskByInstrumentNameAndPriceAndQuantity(String instrumentName, BigDecimal price, int quantity);
}
