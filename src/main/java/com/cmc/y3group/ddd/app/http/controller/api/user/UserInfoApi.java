package com.cmc.y3group.ddd.app.http.controller.api.user;

import com.cmc.y3group.ddd.app.http.middleware.Authenticate;
import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.ID;
import static com.cmc.y3group.ddd.config.constants.AuthConstant.USERNAME;

@Component
public class UserInfoApi extends Controller {
	/**
	 * Path.
	 */
	public static String PATH = "/user/info";

	public UserInfoApi() {
		middlewares = new Class[]{
			Authenticate.class
		};
	}

	@Override
	public void handle(RoutingContext event) {
		JsonObject jsonObject = event.user().principal();
		UserResponse userResponse = new UserResponse();
		userResponse.setId(jsonObject.getLong(ID));
		userResponse.setUsername(jsonObject.getString(USERNAME));

		Response res = new Response();
		res.setMessage("successfully.");
		res.setCode(Response.CODE.OK);
		res.setStatus(Response.STATUS.SUCCESS);
		res.setData(userResponse);
		event.json(res);
	}
}
