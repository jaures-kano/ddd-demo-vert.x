package com.cmc.y3group.ddd.domain.subdomain.order.service.impl;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderDomainEvent;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEvent;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEventBuilder;
import com.cmc.y3group.ddd.domain.subdomain.order.factories.OrderBuilder;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.repositories.OrderRepository;
import com.cmc.y3group.ddd.domain.subdomain.order.service.OrderService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class OrderServiceImpl implements OrderService {
	@Autowired
	private CartService cartService;

	@Autowired
	private OrderRepository orderRepository;

	public OrderServiceImpl() {
		DomainEvents.evtBus.consumer(OrderDomainEvent.TOPIC, this::onOrderEvent);
	}

	private void onOrderEvent(Message<ArrayList<OrderEvent>> msg) {
		ArrayList<OrderEvent> events = msg.body();

		ArrayList<Order> orders = new ArrayList<>();

		for (OrderEvent event : events) {
			if (OrderEvent.TYPE.CREATE.equals(event.getType()) ||
				OrderEvent.TYPE.UPDATE.equals(event.getType())
			)
				orders.add(event.getOrder());
		}

		if (!orders.isEmpty())
			orderRepository.saveAll(orders);
	}


	@Transactional
	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order createOrder(User user, OrderDTO orderDTO) {
		Cart cart = cartService.findCartByUser(user);
		if (Objects.isNull(cart) || cart.getItems().isEmpty())
			return null;

		OrderBuilder orderBuilder = OrderBuilder.builder(cart, orderDTO)
			.setUserId(user.getId());

		Order order = orderBuilder.build();

		// Raise
		OrderEventBuilder orderEvtBuilder = OrderEventBuilder.builder()
			.setOrder(order)
			.setType(OrderEvent.TYPE.CREATE);
		OrderEvent orderEvt = orderEvtBuilder.build();
		DomainEvents.Order.raise(orderEvt);

		cart.clean();
		return order;
	}

	@Override
	public Page<Order> findByFilter(User user, AppFilter filter) {
		int pageNumber = Math.max(0, filter.getPageNumber() - 1);
		Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize(), Sort.Direction.DESC, "updatedAt");
		return orderRepository.findAllByUserId(user.getId(), pageable);
	}
}
