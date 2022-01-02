package com.cmc.y3group.ddd.domain.subdomain.order.factories;

import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderBuyer;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class BuyerBuilder {
	private String id;
	private String fullName;
	private String phone;
	private String email;
	private Order order;

	private BuyerBuilder() {
	}

	public static BuyerBuilder builder() {
		return new BuyerBuilder();
	}

	public BuyerBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public BuyerBuilder setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public BuyerBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public BuyerBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public BuyerBuilder setOrder(Order order) {
		this.order = order;
		return this;
	}

	public OrderBuyer build() {
		OrderBuyer buyer = new OrderBuyer();
		if (Objects.isNull(id)) id = UUID.randomUUID().toString();

		buyer.setId(id);
		buyer.setFullName(fullName);
		buyer.setPhone(phone);
		buyer.setEmail(email);
		buyer.setOrder(order);
		return buyer;
	}
}
