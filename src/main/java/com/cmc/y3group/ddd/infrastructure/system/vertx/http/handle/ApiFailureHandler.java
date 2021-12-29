package com.cmc.y3group.ddd.infrastructure.system.vertx.http.handle;

import com.cmc.y3group.ddd.app.models.response.Response;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class ApiFailureHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext rc) {
		Response response = new Response();
		response.setMessage("API errr");
		rc.json(response);
	}
}
