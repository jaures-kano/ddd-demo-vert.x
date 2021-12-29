package com.cmc.y3group.ddd.app.http.controller.api.cart;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.app.service.AppProductService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.ID;

@Component
public class CartRemoveApi extends Controller {
	@Autowired
	private AppCartService appCartService;

	/**
	 * Path.
	 */
	public static String PATH = "/cart/remove";

//	public ProductApi() {
//		middlewares = new Class[]{
//			Authenticate.class
//		};
//	}

	@Override
	public void handle(RoutingContext evt) {
		MultiMap params = evt.request().params();
		String productId = params.get(ID);

		User user = evt.user();
		Long userId = user.principal().getLong(ID);

		appCartService.remove(userId, Long.valueOf(productId))
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
