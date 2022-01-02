package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.response.cart.CartResponse;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;

public interface AppCartService {
	Future<CartResponse> getCart(User user);

	Future<CartResponse> addProductToCart(User user, String productId);

	Future<CartResponse> remove(User user, String productId);
}
