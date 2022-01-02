package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.response.order.OrderResponse;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;

public interface AppOrderService {
	Future<OrderResponse> create(User user, OrderDTO orderDTO);

	Future<Pagination<OrderResponse>> findByFilter(User user, AppFilter filter);
}
