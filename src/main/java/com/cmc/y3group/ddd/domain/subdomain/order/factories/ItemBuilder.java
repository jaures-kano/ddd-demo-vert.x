package com.cmc.y3group.ddd.domain.subdomain.order.factories;

import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderItem;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class ItemBuilder {
	private String id;
	private String productId;
	private String title;
	private Integer quantity;
	private Double price;
	private String image;
	private String voucher;
	private String discription;
	private Order order;

	private ItemBuilder() {
	}

	public ItemBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public ItemBuilder setProductId(String productId) {
		this.productId = productId;
		return this;
	}

	public ItemBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public ItemBuilder setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public ItemBuilder setPrice(Double price) {
		this.price = price;
		return this;
	}

	public ItemBuilder setImage(String image) {
		this.image = image;
		return this;
	}

	public ItemBuilder setVoucher(String voucher) {
		this.voucher = voucher;
		return this;
	}

	public ItemBuilder setDiscription(String discription) {
		this.discription = discription;
		return this;
	}

	public ItemBuilder setOrder(Order order) {
		this.order = order;
		return this;
	}

	public static ItemBuilder builder() {
		return new ItemBuilder();
	}

	public OrderItem build() {
		OrderItem item = new OrderItem();
		if (Objects.isNull(id))
			id = UUID.randomUUID().toString();

		item.setId(id);
		item.setProductId(productId);
		item.setTitle(title);
		item.setQuantity(quantity);
		item.setPrice(price);
		item.setImage(image);
		item.setVoucher(voucher);
		item.setDiscription(discription);
		item.setOrder(order);
		return item;
	}
}
