package com.cmc.y3group.ddd.app.http;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware.*;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;

public final class HttpKernel {
	/**
	 * Middleware for Api.
	 */
	public static Class<?>[] middlewareApi = new Class<?>[]{
		GuardApiMiddleware.class
	};

	/**
	 * Middleware for Web.
	 */
	public static Class<?>[] middlewareWeb = new Class<?>[]{
		SessionHandler.class,
//		CSRFHandler.class,
		GuardMiddleware.class
	};

	/**
	 * Middleware global.
	 */
	public static Class<?>[] middlewares = new Class<?>[]{
		BodyHandler.class,
	};
}
