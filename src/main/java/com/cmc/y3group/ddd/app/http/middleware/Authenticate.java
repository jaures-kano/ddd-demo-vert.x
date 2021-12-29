package com.cmc.y3group.ddd.app.http.middleware;

import com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware.AuthenticateMiddleware;
import org.springframework.stereotype.Component;

@Component
public class Authenticate extends AuthenticateMiddleware {
	private String redirectUrl = "/";

	@Override
	protected String getRedirectUrl() {
		return redirectUrl;
	}
}
