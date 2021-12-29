package com.cmc.y3group.ddd.infrastructure.system.vertx.http;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;

@Getter
public abstract class Controller implements Handler<RoutingContext> {
	/** Middlewares. */
	protected Class<? extends Handler<RoutingContext>>[] middlewares;
}
