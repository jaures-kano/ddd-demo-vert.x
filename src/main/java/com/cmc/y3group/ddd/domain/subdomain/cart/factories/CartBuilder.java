package com.cmc.y3group.ddd.domain.subdomain.cart.factories;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import lombok.Getter;

import java.util.List;

@Getter
public class CartBuilder {
	private String id;
	private List<CartItem> items;

	private CartBuilder() {
	}

	public CartBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public CartBuilder setItems(List<CartItem> items) {
		this.items = items;
		return this;
	}

	public static CartBuilder builder() {
		return new CartBuilder();
	}

	public Cart build() {
		Cart cart = new Cart();
		cart.setId(id);
		cart.setItems(items);
		return cart;
	}
}
