package com.cmc.y3group.ddd.domain.subdomain.order.event;

import com.cmc.y3group.ddd.domain.event.DomainEvent;
import com.cmc.y3group.ddd.domain.event.DomainEvents;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.concurrent.TimeUnit;

public class OrderDomainEvent implements DomainEvent<OrderEvent> {
	public static final String TOPIC = "order";
	/**
	 * Subject.
	 */
	private final BehaviorSubject<OrderEvent> subj;

	public OrderDomainEvent() {
		subj = BehaviorSubject.create();
		subj.buffer(100, TimeUnit.MILLISECONDS, 500)
			.subscribe(events -> {
				if (!events.isEmpty())
					DomainEvents.evtBus.publish(TOPIC, events);
			});
	}

	@Override
	public void raise(OrderEvent evt) {
		subj.onNext(evt);
	}
}
