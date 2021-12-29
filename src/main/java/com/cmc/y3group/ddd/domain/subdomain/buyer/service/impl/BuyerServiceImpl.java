package com.cmc.y3group.ddd.domain.subdomain.buyer.service.impl;

import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.subdomain.buyer.service.BuyerService;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEvent;
import io.vertx.core.eventbus.Message;
import org.springframework.stereotype.Component;

@Component
public class BuyerServiceImpl implements BuyerService {
	public BuyerServiceImpl() {
		DomainEvents.evtBus.consumer(OrderEvent.class.getSimpleName(), this::onMessage);
	}

	private <T> void onMessage(Message<T> tMsg) {
		System.out.println(tMsg);
	}
}
