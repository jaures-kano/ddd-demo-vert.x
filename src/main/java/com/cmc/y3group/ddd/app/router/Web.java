package com.cmc.y3group.ddd.app.router;

import com.cmc.y3group.ddd.app.http.controller.web.user.authentication.SignInController;
import com.cmc.y3group.ddd.app.http.controller.web.user.authentication.SignUpController;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.WebRouter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Web extends WebRouter {
	@PostConstruct
	public void registry() {
//		get("/", HomeController.class);
		post("/signin", SignInController.class);
		post("/signup", SignUpController.class);
	}
}
