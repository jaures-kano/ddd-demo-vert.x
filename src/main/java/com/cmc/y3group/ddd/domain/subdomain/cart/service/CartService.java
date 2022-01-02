package com.cmc.y3group.ddd.domain.subdomain.cart.service;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;

public interface CartService {
	void save(Cart cart);

	void remove(Cart cart);

	Cart findCartByUser(User user);
	Cart findCartByUserId(String userId);
	Cart addProductToCart(User user, String productId);
	Cart removeProduct(User user, String productId);
}
