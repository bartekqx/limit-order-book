package com.bartek.sulima.soft.infrastructure.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserId(String userId);


    @Query("SELECT o FROM OrderEntity o WHERE o.createTime >= ?1 order by o.createTime")
    List<OrderEntity> findOrdersInInterval(Instant interval);

}
