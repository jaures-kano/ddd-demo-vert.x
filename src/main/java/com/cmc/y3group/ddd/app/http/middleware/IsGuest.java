package com.cmc.y3group.ddd.app.http.middleware;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware.GuestMiddleware;
import org.springframework.stereotype.Component;

@Component
public class IsGuest extends GuestMiddleware {
	private String redirectUrl = "/";

	@Override
	protected String getRedirectUrl() {
		return redirectUrl;
	}
}
