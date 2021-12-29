package com.cmc.y3group.ddd.infrastructure.system.vertx.http;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.router.RouteRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RouterBoot {
	/**
	 * Default constructor.
	 */
	public RouterBoot() {
		log.info("Registry router successfully.");
	}

	protected abstract RouteRegistry route();

	protected void get(String path, Class<?>... middlewares) {
		route().get(path, middlewares);
	}

	protected void post(String path, Class<?>... middlewares) {
		route().post(path, middlewares);
	}

	protected void put(String path, Class<?>... middlewares) {
		route().put(path, middlewares);
	}

	protected void delete(String path, Class<?>... middlewares) {
		route().delete(path, middlewares);
	}
}
