package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import io.vertx.core.Future;
import org.springframework.data.domain.Page;

public interface AppOrderService {
	Future<Order> create(Long userId, OrderDTO orderDTO);
	Future<Pagination> findByFilter(AppFilter filter);
}
