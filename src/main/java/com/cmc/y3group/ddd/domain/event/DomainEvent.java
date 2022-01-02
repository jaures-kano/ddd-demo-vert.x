package com.cmc.y3group.ddd.domain.event;

public interface DomainEvent<T> {
	void raise(T evt);
}
