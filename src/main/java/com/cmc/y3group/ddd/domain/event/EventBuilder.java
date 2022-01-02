package com.cmc.y3group.ddd.domain.event;

public class EventBuilder {
	private Event.TYPE type;

	private Object body;

	private EventBuilder() {
	}

	public static EventBuilder builder() {
		return new EventBuilder();
	}

	public EventBuilder setType(Event.TYPE type) {
		this.type = type;
		return this;
	}

	public EventBuilder setBody(Object body) {
		this.body = body;
		return this;
	}

	public Event build() {
		Event event = new Event();
		assert type != null;
		assert body != null;

		event.setType(type);
		event.setBody(body);
		return event;
	}
}
