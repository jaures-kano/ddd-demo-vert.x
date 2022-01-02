package com.cmc.y3group.ddd.app.http.controller;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class PingController extends Controller {
	public static String PATH = "/ping";

	@Override
	public void handle(RoutingContext ctx) {
		ctx.end("ping");
	}
}
