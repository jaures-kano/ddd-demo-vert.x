package com.cmc.y3group.ddd.app.http.controller.api.user;

import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.app.service.AppUserService;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.FormSignupDTO;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SignUpApi extends Controller {
	/**
	 * Path.
	 */
	public static String PATH = "/user/signup";

	@Autowired
	private AppUserService appUserService;

	@Override
	public void handle(RoutingContext ctx) {
		FormSignupDTO formSignupDTO = MappingUtils.requestMapping(ctx, FormSignupDTO.class);
		if (Objects.isNull(formSignupDTO)) {
			Response res = new Response();
			res.setMessage(HttpResponseStatus.BAD_REQUEST.reasonPhrase());
			res.setCode(HttpResponseStatus.BAD_REQUEST.code());
			res.setStatus(Response.STATUS.FAILURE);
			ctx.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
			ctx.json(res);
			return;
		}

		SocketAddress sockAddr = ctx.request().remoteAddress();
		formSignupDTO.setIp(sockAddr.host());
		formSignupDTO.setPort(sockAddr.port());
		formSignupDTO.setAgent(ctx.get("User-Agent"));

		Future<UserResponse> resFut = appUserService.signup(formSignupDTO);
		resFut.onSuccess(userResponse -> onSuccess(ctx, userResponse));
		resFut.onFailure(err -> onFailure(ctx, err));
	}

	private void onSuccess(RoutingContext ctx, UserResponse userResponse) {
		Response res = new Response();
		res.setMessage("Register successfully.");
		res.setCode(Response.CODE.OK);
		res.setStatus(Response.STATUS.SUCCESS);
		res.setData(userResponse);
		ctx.json(res);
	}

	private void onFailure(RoutingContext event, Throwable throwable) {
		Response res = new Response();
		res.setMessage("User is exist.");
		res.setCode(HttpResponseStatus.UNAUTHORIZED.code());
		res.setStatus(Response.STATUS.FAILURE);
		event.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code());
		event.json(res);
	}
}
