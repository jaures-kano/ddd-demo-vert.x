package com.cmc.y3group.ddd.app.http.controller.api.order;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppOrderService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class OrderCreateApi extends Controller {
	@Autowired
	private AppOrderService appOrderService;
	/**
	 * Path.
	 */
	public static final String PATH = "/order/create";

	public OrderCreateApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	@SneakyThrows
	@Override
	public void handle(RoutingContext evt) {
		OrderDTO orderDTO = MappingUtils.requestMapping(evt, OrderDTO.class);
		User user = OBJ_MAPPER.readValue(evt.user().principal().toString(), User.class);

		appOrderService.create(user, orderDTO)
			.onSuccess(orderRes -> {
				if (Objects.isNull(orderRes)) {
					Response res = new Response();
					res.setMessage("failure.");
					res.setCode(Response.CODE.ERROR);
					res.setStatus(Response.STATUS.FAILURE);
					evt.json(res);
				} else {
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
