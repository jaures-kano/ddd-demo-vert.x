package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.response.cart.CartResponse;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppCartServiceImpl implements AppCartService {
	@Autowired
	private CartService cartService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Future<CartResponse> getCart(User user) {
		return Future.future(future -> {
			Cart cart = cartService.findCartByUser(user);
			future.complete(new CartResponse(cart));
		});
	}

	@Override
	public Future<CartResponse> addProductToCart(User user, String productId) {
		return Future.future(future -> {
			Cart cart = cartService.addProductToCart(user, productId);
			if (Objects.isNull(cart)) {
				future.complete(null);
				return;
			}
			future.complete(new CartResponse(cart));
		});
	}

	@Override
	public Future<CartResponse> remove(User user, String productId) {
		return Future.future(future -> {
			Cart cart = cartService.removeProduct(user, productId);
			future.complete(new CartResponse(cart));
		});
	}
}
