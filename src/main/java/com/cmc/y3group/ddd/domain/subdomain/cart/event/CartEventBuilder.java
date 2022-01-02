package com.cmc.y3group.ddd.domain.subdomain.cart.event;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;

public class CartEventBuilder {
	private CartEvent.TYPE type;
	private String userId;
	private Object data;

	private CartEventBuilder() {
	}


	public CartEventBuilder setType(CartEvent.TYPE type) {
		this.type = type;
		return this;
	}

	public CartEventBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public CartEventBuilder setData(Object data) {
		this.data = data;
		return this;
	}

	public static CartEventBuilder builder(){
		return new CartEventBuilder();
	}

	public CartEvent build(){
		CartEvent cartEvt = new CartEvent();
		cartEvt.setType(type);
		cartEvt.setUserId(userId);
		cartEvt.setData(data);
		return cartEvt;
	}
}
