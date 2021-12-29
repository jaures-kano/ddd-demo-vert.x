package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import io.vertx.core.Future;

public interface AppCartService {
	Future<Cart> getCart(Long userId);

	Future<Long> add(Long userId, Long productId);
	Future<Long> remove(Long userId, Long productId);
}
