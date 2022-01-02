package com.cmc.y3group.ddd.domain.subdomain.cart.factories;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ItemBuilder {
	private String id;
	private Integer quantity = 1;
	private Product product;
	private Cart cart;

	private ItemBuilder() {
	}

	public ItemBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public ItemBuilder setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public ItemBuilder setProduct(Product product) {
		this.product = product;
		return this;
	}

	public ItemBuilder setCart(Cart cart) {
		this.cart = cart;
		return this;
	}

	public static ItemBuilder builder() {
		return new ItemBuilder();
	}

	public CartItem build() {
		CartItem item = new CartItem();

		if (Objects.isNull(id))
			id = UUID.randomUUID().toString();

		item.setId(id);
		item.setQuantity(quantity);
		item.setCartId(cart.getId());
		item.setProduct(product);

		Timestamp now = Timestamp.from(Instant.now());
		item.setCreatedAt(now);
		item.setUpdatedAt(now);
		return item;
	}
}
