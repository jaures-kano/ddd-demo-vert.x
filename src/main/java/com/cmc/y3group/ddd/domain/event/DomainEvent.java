package com.cmc.y3group.ddd.domain.event;

import com.cmc.y3group.ddd.infrastructure.system.vertx.eventbus.GenericCodec;

public class DomainEvent<T> {
	/**
	 * @param clazz Clazz.
	 */
	DomainEvent(Class<T> clazz) {
		DomainEvents.evtBus.registerDefaultCodec(clazz, new GenericCodec<>(clazz));
	}

	/**
	 * @param evt Event.
	 */
	public <T> void Raise(T evt) {
		DomainEvents.evtBus.publish(evt.getClass().getSimpleName(), evt);
	}
}
