package com.cmc.y3group.ddd.domain.subdomain.buyer.repository;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuyerRepository extends JpaRepository<Order, Long> {
}
