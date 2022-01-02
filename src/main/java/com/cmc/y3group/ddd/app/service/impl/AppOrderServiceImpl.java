package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.response.order.OrderResponse;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.app.service.AppOrderService;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.service.OrderService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppOrderServiceImpl implements AppOrderService {
	@Autowired
	private OrderService orderService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Future<OrderResponse> create(User user, OrderDTO orderDTO) {
		return Future.future(promise -> {
			Order order = orderService.createOrder(user, orderDTO);
			promise.complete(new OrderResponse(order));
		});
	}

	@Override
	public Future<Pagination<OrderResponse>> findByFilter(User user, AppFilter filter) {
		return Future.future(future -> {
			Page<Order> page = orderService.findByFilter(user, filter);

			List<OrderResponse> orderResponses = new ArrayList<>();

			if (!page.getContent().isEmpty()) {
				for (Order order : page.getContent())
					orderResponses.add(new OrderResponse(order));
			}

			future.complete(new Pagination<>(orderResponses, page.getTotalPages(), page.getTotalElements()));
		});
	}
}
