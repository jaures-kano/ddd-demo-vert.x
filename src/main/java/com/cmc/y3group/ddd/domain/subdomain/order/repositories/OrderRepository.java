package com.cmc.y3group.ddd.domain.subdomain.order.repositories;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
