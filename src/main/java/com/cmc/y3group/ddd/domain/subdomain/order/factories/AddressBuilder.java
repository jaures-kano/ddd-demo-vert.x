package com.cmc.y3group.ddd.domain.subdomain.order.factories;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderAddress;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class AddressBuilder {
	private String id;
	private String address;
	private Order order;

	private AddressBuilder() {
	}

	public static AddressBuilder builder() {
		return new AddressBuilder();
	}

	public AddressBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public AddressBuilder setAddress(String address) {
		this.address = address;
		return this;
	}

	public AddressBuilder setOrder(Order order) {
		this.order = order;
		return this;
	}

	public OrderAddress build() {
		OrderAddress addr = new OrderAddress();
		if (Objects.isNull(id))
			id = UUID.randomUUID().toString();

		addr.setId(id);
		addr.setOrder(order);
		addr.setAddress(address);
		return addr;
	}
}
