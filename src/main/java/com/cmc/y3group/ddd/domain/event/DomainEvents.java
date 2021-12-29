package com.cmc.y3group.ddd.domain.event;

import com.cmc.y3group.ddd.config.VertxConfiguration;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEvent;
import io.vertx.core.eventbus.EventBus;

public final class DomainEvents {
	/** Event bus. */
	public static final EventBus evtBus = VertxConfiguration.getInstance().getVertx().eventBus();

	/** Order. */
	public static DomainEvent<?> Order = new DomainEvent<>(OrderEvent.class);
}
