package com.cmc.y3group.ddd.domain.subdomain.cart.event;

import lombok.Data;

@Data
public class CartEvent {
	public enum TYPE {
		CREATE,
		INCREMENT,
		DECREMENT,
		ADD,
		REMOVE,
		CLEAN
	}

	private TYPE type;
	private String userId;
	private Object data;
}
