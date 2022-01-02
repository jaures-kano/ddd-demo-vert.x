package com.cmc.y3group.ddd.domain.subdomain.cart.event;

import com.cmc.y3group.ddd.domain.event.DomainEvent;
import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CartDomainEvent implements DomainEvent<CartEvent> {
	public static final String TOPIC = "cart";
	/**
	 * Subject.
	 */
	private final BehaviorSubject<CartEvent> subj;

	private LoadingCache<String, BehaviorSubject<CartEvent>> cache = CacheBuilder.newBuilder()
		.maximumSize(1000)
		.expireAfterWrite(10, TimeUnit.MINUTES)
		.build(new CacheLoader<>() {
			@Override
			public BehaviorSubject<CartEvent> load(String key) {
				BehaviorSubject<CartEvent> behaviorSubj = BehaviorSubject.create();
				behaviorSubj.buffer(100, TimeUnit.MILLISECONDS, 500).subscribe(events -> {
					if (!events.isEmpty()) DomainEvents.evtBus.publish(TOPIC, events);
				});
				return behaviorSubj;
			}
		});

	public CartDomainEvent() {
		subj = BehaviorSubject.create();
		subj.groupBy(CartEvent::getUserId).subscribe(groupedObservable -> {
			BehaviorSubject<CartEvent> behaviorSubj = cache.get(Objects.requireNonNull(groupedObservable.getKey()));
			groupedObservable.subscribe(behaviorSubj::onNext);
		});
	}

	@Override
	public void raise(CartEvent evt) {
		subj.onNext(evt);
	}
}
