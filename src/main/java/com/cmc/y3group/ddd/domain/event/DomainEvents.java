package com.cmc.y3group.ddd.domain.event;

import com.cmc.y3group.ddd.config.VertxConfiguration;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartDomainEvent;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartEvent;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderDomainEvent;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEvent;
import com.cmc.y3group.ddd.domain.subdomain.product.event.ProductDomainEvent;
import io.vertx.core.eventbus.EventBus;

public final class DomainEvents {
	/** Event bus. */
	public static final EventBus evtBus = VertxConfiguration.getInstance().getVertx().eventBus();

	public static final DomainEvent<CartEvent> Cart = new CartDomainEvent();
	public static final DomainEvent<OrderEvent> Order = new OrderDomainEvent();
	public static final DomainEvent<Event> Product = new ProductDomainEvent();
}
