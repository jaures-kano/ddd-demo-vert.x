package com.cmc.y3group.ddd.infrastructure.system.vertx.http.handle;

import com.cmc.y3group.ddd.app.models.response.Response;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FailureHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext rc) {
		log.error(rc.failure().getMessage(), rc.failure());
		Response response = new Response();
		response.setMessage("FailureHandler");
		rc.json(response);
	}
}
