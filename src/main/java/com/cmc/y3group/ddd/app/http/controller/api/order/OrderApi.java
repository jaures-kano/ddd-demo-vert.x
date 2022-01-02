package com.cmc.y3group.ddd.app.http.controller.api.order;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
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

import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class OrderApi extends Controller {
	@Autowired
	private AppOrderService appOrderService;
	/**
	 * Path.
	 */
	public static final String PATH = "/order";

	public OrderApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	@SneakyThrows
	@Override
	public void handle(RoutingContext evt) {
		AppFilter filter = MappingUtils.requestMapping(evt, AppFilter.class, true);
		User user = OBJ_MAPPER.readValue(evt.user().principal().toString(), User.class);

		appOrderService.findByFilter(user, filter)
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
