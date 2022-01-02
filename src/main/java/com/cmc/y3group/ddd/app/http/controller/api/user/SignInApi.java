package com.cmc.y3group.ddd.app.http.controller.api.user;

import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.app.service.AppUserService;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
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
public class SignInApi extends Controller {
	public static String PATH = "/user/signin";

	@Autowired
	private AppUserService appUserService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RoutingContext ctx) {
		CredentialDTO credentialDTO = MappingUtils.requestMapping(ctx, CredentialDTO.class);

		if (Objects.isNull(credentialDTO)) {
			Response res = new Response();
			res.setMessage(HttpResponseStatus.BAD_REQUEST.reasonPhrase());
			res.setCode(HttpResponseStatus.BAD_REQUEST.code());
			res.setStatus(Response.STATUS.FAILURE);
			ctx.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
			ctx.json(res);
			return;
		}
		SocketAddress sockAddr = ctx.request().remoteAddress();
		credentialDTO.setIp(sockAddr.host());
		credentialDTO.setPort(sockAddr.port());
		credentialDTO.setAgent(ctx.get("User-Agent"));

		Future<UserResponse> result = appUserService.signin(credentialDTO);

		result.onSuccess(signInSuccessUser -> this.handleSuccess(ctx, signInSuccessUser));
		result.onFailure(err -> this.handleFailure(ctx, err));
	}

	private void handleSuccess(RoutingContext evt, UserResponse userRes) {
		Response res = new Response();
		res.setMessage("Login successfully.");
		res.setCode(Response.CODE.OK);
		res.setStatus(Response.STATUS.SUCCESS);
		res.setData(userRes);
		evt.json(res);
	}

	/**
	 * @param evt       Event.
	 * @param throwable Throwable.
	 */
	private void handleFailure(RoutingContext evt, Throwable throwable) {
		Response res = new Response();
		res.setMessage(throwable.getMessage());
		res.setCode(HttpResponseStatus.UNAUTHORIZED.code());
		res.setStatus(Response.STATUS.FAILURE);
		evt.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code());
		evt.json(res);
	}
}
