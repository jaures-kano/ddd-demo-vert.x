package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.OrderResponse;
import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.app.service.AppOrderService;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderAddress;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderItem;
import com.cmc.y3group.ddd.domain.subdomain.order.repositories.OrderRepository;
import com.cmc.y3group.ddd.domain.subdomain.order.service.OrderService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AppOrderServiceImpl implements AppOrderService {
	@Autowired
	private AppCartService appCartService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Future<Order> create(Long userId, OrderDTO orderDTO) {
		return Future.future(promise -> {
			Future<Cart> cartFut = appCartService.getCart(userId);
			cartFut.onSuccess(cart -> {
				if (Objects.isNull(cart) || cart.getItems().isEmpty()) {
					promise.complete(null);
					return;
				}

				Order order = new Order();
				order.setOrderService(orderService);
				order.setPhone(orderDTO.getPhone());

				// User
				User user = new User();
				user.setId(userId);
				order.setUser(user);

				List<OrderItem> orderItemList = new ArrayList<>();
				order.setItems(orderItemList);

				// Items
				for (CartItem cartItem : cart.getItems()) {
					OrderItem orderItem = new OrderItem();
					orderItem.setName(cartItem.getProduct().getName());
					orderItem.setDiscription(cartItem.getProduct().getDiscription());
					orderItem.setPrice(cartItem.getProduct().getPrice());
					orderItem.setOrder(order);
					orderItemList.add(orderItem);
				}

				// Address
				OrderAddress orderAddr = new OrderAddress();
				orderAddr.setAddress(orderDTO.getAddress());
				orderAddr.setOrder(order);
				order.setOrderAddress(orderAddr);

				order.create();

				cart.setCartService(cartService);
				cart.clear();
				promise.complete(order);
			});
		});
	}

	@Transactional
	@Override
	public Future<Pagination> findByFilter(AppFilter filter) {
		return Future.future(future -> {
			int pageNumber = Math.max(0, filter.getPageNumber() - 1);
			Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());
			Page<Order> page = orderRepository.findAll(pageable);

			Pagination<Order, OrderResponse> pagination = new Pagination<>(page, this::convertOrderToOrderResponse);
			future.complete(pagination);
		});
	}

	public OrderResponse convertOrderToOrderResponse(Order order){
		OrderResponse response = new OrderResponse();
		if (!Objects.isNull(order.getOrderAddress()))
			response.setAddress(order.getOrderAddress().getAddress());

		response.setId(order.getId());
		response.setQuantity(order.getQuantity());
		response.setPhone(order.getPhone());
		response.setPrice(order.getPrice());
//		response.setItems(order.getItems());
		return response;
	}
}
