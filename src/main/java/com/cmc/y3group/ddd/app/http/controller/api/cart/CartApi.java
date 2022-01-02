package com.cmc.y3group.ddd.app.http.controller.api.cart;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Component
public class CartApi extends Controller {
	@Autowired
	private AppCartService appCartService;

	/**
	 * Path.
	 */
	public static String PATH = "/cart";

	public CartApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	@SneakyThrows
	@Override
	public void handle(RoutingContext evt) {
		User user = OBJ_MAPPER.readValue(evt.user().principal().toString(), User.class);
		appCartService.getCart(user)
			.onSuccess(cart -> {
				Response res = new Response();
				res.setMessage("successfully.");
				res.setCode(Response.CODE.OK);
				res.setStatus(Response.STATUS.SUCCESS);
				res.setData(cart);
				evt.json(res);
			})
			.onFailure(evt::fail);
	}
}
