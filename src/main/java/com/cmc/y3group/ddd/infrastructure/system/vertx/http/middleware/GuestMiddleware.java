package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.util.Objects;

public abstract class GuestMiddleware implements Handler<RoutingContext> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle(RoutingContext evt) {
		if (!Objects.isNull(evt.user())) {
			evt.redirect(getRedirectUrl());
			evt.end();
			return;
		}
		evt.next();
	}

	abstract protected String getRedirectUrl();
}
