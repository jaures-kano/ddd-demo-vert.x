package com.cmc.y3group.ddd.infrastructure.system.vertx.http;

import com.cmc.y3group.ddd.app.http.HttpKernel;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.router.RouteRegistry;

public abstract class WebRouter extends RouterBoot {
	public static String NAME_WEB_ROUTER = "WEB";

	/**
	 * Route.
	 */
	public final RouteRegistry route = new RouteRegistry(
		new RouteRegistry.Options()
			.setName(NAME_WEB_ROUTER),
		HttpKernel.middlewareWeb);

	@Override
	protected RouteRegistry route() {
		return route;
	}
}
