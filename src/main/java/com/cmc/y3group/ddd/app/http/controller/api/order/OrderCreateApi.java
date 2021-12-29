package com.cmc.y3group.ddd.app.http.controller.api.order;

import com.cmc.y3group.ddd.app.models.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.response.OrderResponse;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppOrderService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.ID;

@Slf4j
@Component
public class OrderCreateApi extends Controller {
	@Autowired
	private AppOrderService appOrderService;
	/**
	 * Path.
	 */
	public static final String PATH = "/order/create";

//	public OrderCreateApi() {
//		middlewares = new Class[]{
//			Authenticate.class
//		};
//	}

	@Override
	public void handle(RoutingContext evt) {
		OrderDTO orderDTO = MappingUtils.requestMapping(evt, OrderDTO.class);
		JsonObject jsonObj = evt.user().principal();
		Long userId = jsonObj.getLong(ID);

		appOrderService.create(userId, orderDTO)
			.onSuccess(order -> {
				if (Objects.isNull(order)) {
					Response res = new Response();
					res.setMessage("failure.");
					res.setCode(Response.CODE.OK);
					res.setStatus(Response.STATUS.FAILURE);
					evt.json(res);
				} else {
					OrderResponse orderRes = new OrderResponse();
					orderRes.setAddress(order.getOrderAddress().getAddress());
					orderRes.setPhone(order.getPhone());
					orderRes.setUpdatedAt(order.getUpdatedAt().toString());
					orderRes.setCreatedAt(order.getCreatedAt().toString());
					List<OrderResponse.OrderItem> orderItemList = order.getItems().stream().map(item -> {
						OrderResponse.OrderItem orderItem = new OrderResponse.OrderItem();
						orderItem.setName(item.getName());
						orderItem.setDiscription(item.getDiscription());
						orderItem.setPrice(item.getPrice());
						return orderItem;
					}).collect(Collectors.toList());
					orderRes.setItems(orderItemList);

					Response res = new Response();
					res.setMessage("successfully.");
					res.setCode(Response.CODE.OK);
					res.setStatus(Response.STATUS.SUCCESS);
					res.setData(orderRes);
					evt.json(res);
				}
			}).onFailure(evt::fail);
	}
}
