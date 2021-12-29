package com.cmc.y3group.ddd.domain.subdomain.order.event;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import lombok.Data;

@Data
public class OrderEventBuilder {
	private OrderEvent.TYPE type;

	private Order order;

	private OrderEventBuilder() {
	}

	/**
	 *
	 */
	public static OrderEventBuilder builder() {
		return new OrderEventBuilder();
	}

	public OrderEventBuilder setType(OrderEvent.TYPE type) {
		this.type = type;
		return this;
	}

	public OrderEventBuilder setOrder(Order order) {
		this.order = order;
		return this;
	}

	public OrderEvent build() {
		OrderEvent orderEvent = new OrderEvent();
		assert type != null;
		assert order != null;

		orderEvent.setType(type);
		orderEvent.setOrder(order);
		return orderEvent;
	}
}
