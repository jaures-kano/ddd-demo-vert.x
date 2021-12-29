package com.cmc.y3group.ddd.domain.subdomain.order.event;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import lombok.Data;

@Data
public class OrderEvent {
	public enum TYPE {
		/**
		 * Create.
		 */
		CREATE,

		/**
		 * Update.
		 */
		UPDATE,

		/**
		 * Delete.
		 */
		DELETE
	}

	OrderEvent() {
	}

	private TYPE type;

	private Order order;
}
