package com.cmc.y3group.ddd.domain.subdomain.cart.repository;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<CartItem, String> {
}
