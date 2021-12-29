package com.cmc.y3group.ddd.app.http.controller.web.user.authentication;

import com.cmc.y3group.ddd.app.http.middleware.IsGuest;
import com.cmc.y3group.ddd.app.models.dto.SignInDTO;
import com.cmc.y3group.ddd.config.constants.AuthConstant;
import com.cmc.y3group.ddd.domain.subdomain.session.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.vertx.core.Future;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.sstore.impl.SharedDataSessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SignInController extends Controller {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	private long expAge = 30 * 24 * 3600 * 1000;

	/**
	 * Default constructor.
	 */
	public SignInController(){
		middlewares = new Class[]{
			IsGuest.class
		};
	}

	@Override
	public void handle(RoutingContext event) {
		SignInDTO signInDTO = MappingUtils.requestMapping(event, SignInDTO.class);
		if (Objects.isNull(signInDTO)) {
			event.end("");
			return;
		}

		UserBuilder builder = UserBuilder.builder();
		builder.setUsername(signInDTO.getUsername());
		builder.setPassword(signInDTO.getPassword());
		builder.setUserService(userService);

		User user = builder.build();
		Future<User> result = user.signin();

		result.onSuccess(signInSuccessUser -> this.handleOnSignInSuccess(event, signInSuccessUser, signInDTO.getRemember_me()));
		result.onFailure(err -> this.handleOnSignInFailure(event, err));
	}

	private void handleOnSignInSuccess(RoutingContext event, User user, boolean remember_me) {
		Session ses = new Session();
		ses.setUser(user);
		ses.setSessionService(sessionService);
		ses.generateSessionKey();

		ses.saveChange();

		io.vertx.ext.web.Session sessionWeb = event.session();
		if (Objects.isNull(sessionWeb))
			sessionWeb = new SharedDataSessionImpl();

		sessionWeb.put(AuthConstant.ID, user.getId());
		event.setSession(sessionWeb);

		String cookieSession = user.getId() + "|" + ses.getSession();
		if (remember_me) {
			Cookie cookie = Cookie.cookie(AuthConstant.SESSION, cookieSession);
			cookie.setPath("/");
//			cookie.setHttpOnly(true);
			cookie.setMaxAge(expAge);
			event.response().addCookie(cookie);
		}

		event.end("Signing successfully.");
	}

	private void handleOnSignInFailure(RoutingContext event, Throwable throwable) {
		event.end(throwable.getMessage());
	}
}
