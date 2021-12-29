package com.cmc.y3group.ddd.app.http.controller.api.cart;

import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.ID;

@Component
public class CartApi extends Controller {
	@Autowired
	private AppCartService appCartService;

	/**
	 * Path.
	 */
	public static String PATH = "/cart";

//	public ProductApi() {
//		middlewares = new Class[]{
//			Authenticate.class
//		};
//	}

	@Override
	public void handle(RoutingContext evt) {
		User user = evt.user();
		Long userId = user.principal().getLong(ID);

		appCartService.getCart(userId)
			.onSuccess(cart -> {
				Response res = new Response();
				res.setMessage("successfully.");
				res.setCode(Response.CODE.OK);
				res.setStatus(Response.STATUS.SUCCESS);

				JsonObject jsonObj = new JsonObject();
				res.setData(jsonObj);

				if (!Objects.isNull(cart)) {
					List<Product> products = new ArrayList<>();
					for (CartItem cartItem : cart.getItems()) products.add(cartItem.getProduct());
					jsonObj.put("items", products);
				}
				evt.json(res);
			})
			.onFailure(evt::fail);
	}
}
