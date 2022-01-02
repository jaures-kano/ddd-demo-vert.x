package com.cmc.y3group.ddd.app.models.response.cart;

import com.cmc.y3group.ddd.app.models.response.product.ProductResponse;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
	private List<ItemResponse> items;
	private Integer totalQuantity;
	private Integer totalItem;
	private Double totalPrice;

	public CartResponse() {
	}

	public CartResponse(Cart cart) {
		if (Objects.isNull(cart))
			return;

		this.totalQuantity = cart.totalQuantity();
		this.totalPrice = cart.totalPrice();
		this.totalItem = cart.totalItem();

		if (!Objects.isNull(cart.getItems()) && !cart.getItems().isEmpty()) {
			for (CartItem item : cart.getItems())
				this.addItem(new ItemResponse(item));
		} else this.setItems(Collections.EMPTY_LIST);
	}

	public void addItem(ItemResponse itemRes) {
		if (Objects.isNull(items)) items = new ArrayList<>();
		items.add(itemRes);
	}

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class ItemResponse {
		private ProductResponse product;
		private Integer quantity;
		private Double price;

		public ItemResponse() {
		}

		public ItemResponse(CartItem item) {
			this.product = new ProductResponse(item.getProduct());
			this.quantity = item.getQuantity();
			this.price = item.getProduct().getPrice() * item.getQuantity();
		}
	}
}
