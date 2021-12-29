package com.cmc.y3group.ddd.infrastructure.system.vertx.http.router;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import lombok.Getter;
import java.util.List;
import java.util.Objects;

@Data
public class RouteRegisteredBuilder {
	private String name;
	private String path;
	private HttpMethod method;
	private List<Class<?>> middlewares;
	private Handler<RoutingContext> failureHandler;

	public RouteRegistered build(){
		Objects.requireNonNull(this.name);
		Objects.requireNonNull(this.path);
		Objects.requireNonNull(this.method);
		Objects.requireNonNull(this.middlewares);

		RouteRegistered routeRegistered = new RouteRegistered();
		routeRegistered.name = this.name;
		routeRegistered.path = this.path;
		routeRegistered.method = this.method;
		routeRegistered.middlewares = this.middlewares;
		routeRegistered.failureHandler = this.failureHandler;

		return routeRegistered;
	}

	@Getter
	public static class RouteRegistered {
		private String name;
		private String path;
		private HttpMethod method;
		private List<Class<?>> middlewares;
		private Handler<RoutingContext> failureHandler;

		private RouteRegistered(){}
	}
}
