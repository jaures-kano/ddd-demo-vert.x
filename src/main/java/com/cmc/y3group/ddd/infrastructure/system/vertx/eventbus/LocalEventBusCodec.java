package com.cmc.y3group.ddd.infrastructure.system.vertx.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.util.concurrent.atomic.AtomicInteger;

public class LocalEventBusCodec<T> implements MessageCodec<T, T> {

	private static final AtomicInteger count = new AtomicInteger();
	private final String name;

	public LocalEventBusCodec() {
		this(LocalEventBusCodec.class.getName() + "-" + count.getAndIncrement());
	}

	public LocalEventBusCodec(String name) {
		this.name = name;
	}

	@Override
	public void encodeToWire(Buffer buffer, T t) {
		throw new UnsupportedOperationException("LocalEventBusCodec cannot only be used for local delivery");
	}

	@Override
	public T decodeFromWire(int pos, Buffer buffer) {
		throw new UnsupportedOperationException("LocalEventBusCodec cannot only be used for local delivery");
	}

	@Override
	public T transform(T instance) {
		return instance;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public byte systemCodecID() {
		return -1;
	}
}
