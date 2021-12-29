package com.cmc.y3group.ddd.app.http.controller.web;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class HomeController extends Controller {
	@Override
	public void handle(RoutingContext context) {
		context.end("Home");
	}
}
