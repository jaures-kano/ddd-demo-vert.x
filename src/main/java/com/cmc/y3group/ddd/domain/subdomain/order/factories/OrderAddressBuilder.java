package com.cmc.y3group.ddd.domain.subdomain.order.factories;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderAddress;
import lombok.Getter;

@Getter
public class OrderAddressBuilder {
	private Long id;
	private String address;
	private Order order;
	private OrderAddressBuilder() {
	}
	public static OrderAddressBuilder builder() {
		return new OrderAddressBuilder();
	}
	public OrderAddressBuilder setId(Long id) {
		this.id = id;
		return this;
	}
	public OrderAddressBuilder setAddress(String address) {
		this.address = address;
		return this;
	}
	public OrderAddressBuilder setOrder(Order order) {
		this.order = order;
		return this;
	}
	public OrderAddress build() {
		OrderAddress orderAddr = new OrderAddress();
		orderAddr.setId(id);
		orderAddr.setOrder(order);
		orderAddr.setAddress(address);
		return orderAddr;
	}
}
