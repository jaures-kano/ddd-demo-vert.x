package com.cmc.y3group.ddd.app.http.controller.api.order;

import com.cmc.y3group.ddd.app.models.dto.OrderDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.OrderResponse;
import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppOrderService;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
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
public class OrderApi extends Controller {
	@Autowired
	private AppOrderService appOrderService;
	/**
	 * Path.
	 */
	public static final String PATH = "/order";

//	public OrderCreateApi() {
//		middlewares = new Class[]{
//			Authenticate.class
//		};
//	}

	@Override
	public void handle(RoutingContext evt) {
		AppFilter filter = MappingUtils.requestMapping(evt, AppFilter.class, true);

		appOrderService.findByFilter(filter)
			.onSuccess(pagination -> {
				Response res = new Response();
				res.setMessage("successfully.");
				res.setCode(Response.CODE.OK);
				res.setStatus(Response.STATUS.SUCCESS);
				res.setData(pagination);
				evt.json(res);
			})
			.onFailure(evt::fail);
	}

}
