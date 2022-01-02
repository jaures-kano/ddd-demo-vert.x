package com.cmc.y3group.ddd.domain.subdomain.cart.repository;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
