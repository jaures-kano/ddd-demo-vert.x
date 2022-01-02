package com.cmc.y3group.ddd.domain.event;

import lombok.Data;

@Data
public class Event {
	public static enum TYPE {
		CREATE,
		UPDATE,
		DELETE
	}

	public Event() {
	}

	private TYPE type;

	private Object body;
}
