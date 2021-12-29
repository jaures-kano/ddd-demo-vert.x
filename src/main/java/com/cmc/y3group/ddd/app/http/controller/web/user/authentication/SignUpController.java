package com.cmc.y3group.ddd.app.http.controller.web.user.authentication;

import com.cmc.y3group.ddd.app.http.middleware.IsGuest;
import com.cmc.y3group.ddd.app.models.dto.SignUpDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SignUpController extends Controller {
	@Autowired
	private UserService userService;

	public SignUpController() {
		middlewares = new Class[]{
			IsGuest.class
		};
	}

	@Override
	public void handle(RoutingContext event) {
		SignUpDTO signUpDTO = MappingUtils.requestMapping(event, SignUpDTO.class);
		if (Objects.isNull(signUpDTO)) {
			event.end("User is required.");
			return;
		}

		UserBuilder builder = UserBuilder.builder();
		builder.setUsername(signUpDTO.getUsername());
		builder.setPassword(signUpDTO.getPassword());
		builder.setUserService(userService);

		User user = builder.build();
		Future<User> result = user.signup();
		result.onSuccess(signUpSuccessUser -> {
			// TODO: create session.
			event.end("Signup successfully");
		});
		result.onFailure(err -> event.end(err.getMessage()));
	}
}
