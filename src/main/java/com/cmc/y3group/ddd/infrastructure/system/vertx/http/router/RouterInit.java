package com.cmc.y3group.ddd.infrastructure.system.vertx.http.router;

import com.cmc.y3group.ddd.app.http.HttpKernel;
import com.cmc.y3group.ddd.config.WebConfiguration;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class RouterInit {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private WebConfiguration webConfiguration;

	public void init(Router router) {
		webConfiguration.config(router);
		errorHandler(router);
		RouteRegistry.routes.forEach(entry -> registry(router, entry));
		log.info("Apply router successfully.");
	}

	public void registry(Router router, RouteRegisteredBuilder.RouteRegistered routeRegistered) {
		Route route = router.route(routeRegistered.getMethod(), routeRegistered.getPath());

		route.setName(routeRegistered.getName());

		Class<?>[] middlewareGlobal = HttpKernel.middlewares;
		for (Class<?> middlewareClass : middlewareGlobal) {
			Handler<RoutingContext> middleware = (Handler<RoutingContext>) context.getBean(middlewareClass);

			if (middleware instanceof BodyHandler &&
				HttpMethod.GET.equals(routeRegistered.getMethod())) continue;

			route.handler(middleware);
		}

		// Route
		List<Class<?>> middlewares = routeRegistered.getMiddlewares();
		for (Class<?> middlewareClass : middlewares) {
			Handler<RoutingContext> middleware = (Handler<RoutingContext>) context.getBean(middlewareClass);
			if (middleware instanceof Controller) {
				Controller controller = (Controller) middleware;
				if (!Objects.isNull(controller.getMiddlewares())) {
					for (Class<? extends Handler<RoutingContext>> classMiddlewaresInController : controller.getMiddlewares()) {
						Handler<RoutingContext> middlewaresInController = context.getBean(classMiddlewaresInController);
						route.handler(middlewaresInController);
					}
				}
			}
			route.handler(middleware);
		}

//		if (!Objects.isNull(routeRegistered.getFailureHandler()))
//			route.failureHandler(routeRegistered.getFailureHandler());
	}

	private void errorHandler(Router router) {
//		router.route().failureHandler(failureHandler);
//		router.errorHandler(HttpResponseStatus.NOT_FOUND.code(), pageNotFoundHandler);
	}
}
