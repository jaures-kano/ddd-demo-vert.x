package com.cmc.y3group.ddd.app.http.controller.api.product;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.service.AppProductService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductApi extends Controller {
	@Autowired
	private AppProductService appProductService;

	/**
	 * Path.
	 */
	public static String PATH = "/product";

//	public ProductApi() {
//		middlewares = new Class[]{
//			Authenticate.class
//		};
//	}

	@Override
	public void handle(RoutingContext evt) {
		AppFilter filter = MappingUtils.requestMapping(evt, AppFilter.class, true);

		appProductService.findByFilter(filter)
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
