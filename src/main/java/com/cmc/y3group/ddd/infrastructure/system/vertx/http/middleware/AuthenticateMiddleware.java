package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import com.cmc.y3group.ddd.app.models.response.Response;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.Objects;

import static com.cmc.y3group.ddd.infrastructure.system.vertx.http.ApiRouter.NAME_API_ROUTER;

public abstract class AuthenticateMiddleware implements Handler<RoutingContext> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RoutingContext evt) {
		if (Objects.isNull(evt.user())) {
			String nameOfCurrentRoute = evt.currentRoute().getName().toUpperCase();
			if (NAME_API_ROUTER.equals(nameOfCurrentRoute)) {
				Response response = new Response();
				response.setCode(Response.STATUS.FAILURE);
				response.setStatus(Response.STATUS.FAILURE);
				response.setMessage(HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
				evt.json(response);
			} else {
				evt.redirect(getRedirectUrl());
				evt.end(HttpResponseStatus.UNAUTHORIZED.reasonPhrase());
			}
			return;
		}
		evt.next();
	}

	abstract protected String getRedirectUrl();
}
