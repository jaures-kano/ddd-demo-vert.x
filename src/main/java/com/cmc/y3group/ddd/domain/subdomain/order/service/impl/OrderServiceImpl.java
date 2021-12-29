package com.cmc.y3group.ddd.domain.subdomain.order.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.repositories.OrderItemRepository;
import com.cmc.y3group.ddd.domain.subdomain.order.repositories.OrderRepository;
import com.cmc.y3group.ddd.domain.subdomain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Transactional
	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}
}
