package com.cmc.y3group.ddd.domain.subdomain.product.event;

import com.cmc.y3group.ddd.domain.event.DomainEvent;
import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.event.Event;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.concurrent.TimeUnit;

public class ProductDomainEvent implements DomainEvent<Event> {
	public static final String TOPIC = "product";
	private final BehaviorSubject<Event> subj;

	public ProductDomainEvent() {
		subj = BehaviorSubject.create();
		subj.buffer(100, TimeUnit.MILLISECONDS, 500)
			.subscribe(events -> {
				if (!events.isEmpty())
					DomainEvents.evtBus.publish(TOPIC, events);
			});
	}

	@Override
	public void raise(Event evt) {
		subj.onNext(evt);
	}
}
