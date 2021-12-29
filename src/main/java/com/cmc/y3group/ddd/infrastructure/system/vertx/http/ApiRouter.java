package com.cmc.y3group.ddd.infrastructure.system.vertx.http;

import com.cmc.y3group.ddd.app.http.HttpKernel;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.router.RouteRegistry;

public abstract class ApiRouter extends RouterBoot {
	public static String NAME_API_ROUTER = "API";
	public static String PREFIX_API = "/api";

	/**
	 * Route.
	 */
	public final RouteRegistry route = new RouteRegistry(
		new RouteRegistry.Options()
			.setName(NAME_API_ROUTER)
			.setPath(PREFIX_API),
		HttpKernel.middlewareApi);

	@Override
	protected RouteRegistry route() {
		return route;
	}
}
