package com.cmc.y3group.ddd.infrastructure.system.vertx.http.router;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class RouteRegistry {

	public static List<RouteRegisteredBuilder.RouteRegistered> routes = new ArrayList<>();

	private String name;
	private String path = "";
	private List<Class<?>> middlewares = new ArrayList<>();

	private Handler<RoutingContext> failureHandler;

	@Getter
	public static class Options {
		private String name;
		private String path;

		public Options setName(String name) {
			this.name = name;
			return this;
		}

		public Options setPath(String path) {
			this.path = path;
			return this;
		}
	}

	public RouteRegistry(Options options, Class<?>... middlewares) {
		this.name = options.name;
		if (!Objects.isNull(options.path))
			this.path = options.path;

		if (!Objects.isNull(middlewares))
			Collections.addAll(this.middlewares, middlewares);
	}

	public RouteRegistry get(String path, Class<?>... middlewares) {
		this.registry(HttpMethod.GET, path, middlewares);
		return this;
	}

	public RouteRegistry post(String path, Class<?>... middlewares) {
		this.registry(HttpMethod.POST, path, middlewares);
		return this;
	}

	public RouteRegistry put(String path, Class<?>... middlewares) {
		this.registry(HttpMethod.PUT, path, middlewares);
		return this;
	}

	public RouteRegistry delete(String path, Class<?>... middlewares) {
		this.registry(HttpMethod.DELETE, path, middlewares);
		return this;
	}

	public RouteRegistry method(HttpMethod method, String path, Class<?>... middlewares) {
		this.registry(method, path, middlewares);
		return this;
	}

	public RouteRegistry group(String path, Class<?>... middlewares) {
		List<Class<?>> listM = Stream.concat(
			this.middlewares.stream(),
			Arrays.stream(middlewares)
		).collect(Collectors.toList());

		path = releasePath(this.path, path);

		Options options = new Options();
		options.name = this.name;
		options.path = path;

		RouteRegistry routeRegistry = new RouteRegistry(options, listM.toArray(new Class[0]));
		return routeRegistry;
	}

	public RouteRegistry middleware(Class<?>... middlewares) {
		List<?> listM = Stream.concat(
			this.middlewares.stream(),
			Arrays.stream(middlewares)
		).collect(Collectors.toList());

		Options options = new Options();
		options.name = this.name;
		options.path = this.path;

		RouteRegistry routeRegistry = new RouteRegistry(options, listM.toArray(new Class[0]));
		return routeRegistry;
	}

	public static String releasePath(String path1, String path2) {
		if (!path1.endsWith("/") && !path2.startsWith("/"))
			return path1 + '/' + path2;

		return path1 + path2;
	}

	public void registry(HttpMethod method, String path, Class<?>... middlewares) {
		path = RouteRegistry.releasePath(this.path, path);

		RouteRegisteredBuilder builder = new RouteRegisteredBuilder();
		builder.setName(this.name);
		builder.setPath(path);
		builder.setMethod(method);
		builder.setFailureHandler(failureHandler);

		builder.setMiddlewares(
			Stream.concat(
				this.middlewares.stream(),
				Arrays.stream(middlewares)
			).collect(Collectors.toList()));

		routes.add(builder.build());
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFailureHandler(Handler<RoutingContext> failureHandler) {
		this.failureHandler = failureHandler;
	}
}
