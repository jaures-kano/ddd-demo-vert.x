package com.cmc.y3group.ddd.domain.subdomain.order.service;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import org.springframework.data.domain.Page;

public interface OrderService {
	void save(Order order);
	Order createOrder(User user, OrderDTO orderDTO);

	Page<Order> findByFilter(User user, AppFilter filter);
}
