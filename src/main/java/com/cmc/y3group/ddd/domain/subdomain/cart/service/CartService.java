package com.cmc.y3group.ddd.domain.subdomain.cart.service;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;

public interface CartService {
	void save(Cart cart);
	void remove(Cart cart);
}
