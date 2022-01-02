package com.cmc.y3group.ddd.app.http.controller.api.cart;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.ID;
import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class CartAddApi extends Controller {
	@Autowired
	private AppCartService appCartService;

	/**
	 * Path.
	 */
	public static String PATH = "/cart/add";

	public CartAddApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	@SneakyThrows
	@Override
	public void handle(RoutingContext evt) {
		MultiMap params = evt.request().params();
		String productId = params.get(ID);

		User user = OBJ_MAPPER.readValue(evt.user().principal().toString(), User.class);

		appCartService.addProductToCart(user, productId)
			.onSuccess(handle -> {
				Response res = new Response();
				res.setMessage("successfully.");
				res.setCode(Response.CODE.OK);
				res.setStatus(Response.STATUS.SUCCESS);
				res.setData(handle);
				evt.json(res);
			})
			.onFailure(evt::fail);
	}
}
