package com.cmc.y3group.ddd.domain.subdomain.cart.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.repository.CartRepository;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Override
	public void save(Cart cart) {
		cartRepository.save(cart);
	}

	@Override
	public void remove(Cart cart) {
		cartRepository.delete(cart);
	}
}
