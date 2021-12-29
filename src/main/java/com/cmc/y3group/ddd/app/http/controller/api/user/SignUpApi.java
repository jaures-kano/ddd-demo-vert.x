package com.cmc.y3group.ddd.app.http.controller.api.user;

import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.app.models.dto.SignUpDTO;
import com.cmc.y3group.ddd.domain.subdomain.session.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import com.cmc.y3group.ddd.infrastructure.support.MappingUtils;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.Controller;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SignUpApi extends Controller {
	/**
	 * Path.
	 */
	public static String PATH = "/user/signup";

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Override
	public void handle(RoutingContext event) {
		SignUpDTO signUpDTO = MappingUtils.requestMapping(event, SignUpDTO.class);
		if (Objects.isNull(signUpDTO)) {
			Response res = new Response();
			res.setMessage(HttpResponseStatus.BAD_REQUEST.reasonPhrase());
			res.setCode(HttpResponseStatus.BAD_REQUEST.code());
			res.setStatus(Response.STATUS.FAILURE);
			event.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
			event.json(res);
			return;
		}

		UserBuilder builder = UserBuilder.builder();
		builder.setUsername(signUpDTO.getUsername());
		builder.setPassword(signUpDTO.getPassword());
		builder.setUserService(userService);

		User user = builder.build();
		Future<User> result = user.signup();
		result.onSuccess(signUpSuccessUser -> onSuccess(event, signUpSuccessUser));
		result.onFailure(err -> onFailure(event, err));
	}

	private void onSuccess(RoutingContext event, User user) {
		Session ses = new Session();
		ses.setUser(user);
		ses.setSessionService(sessionService);
		ses.generateSessionKey();
		ses.saveChange();
		String cookieSession = user.getId() + "|" + ses.getSession();

		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setUsername(user.getUsername());
		userResponse.setSession(cookieSession);

		Response res = new Response();
		res.setMessage("Register successfully.");
		res.setCode(Response.CODE.OK);
		res.setStatus(Response.STATUS.SUCCESS);
		res.setData(userResponse);
		event.json(res);
	}

	private void onFailure(RoutingContext event, Throwable throwable) {
		Response res = new Response();
		res.setMessage("User is exist.");
		res.setCode(HttpResponseStatus.UNAUTHORIZED.code());
		res.setStatus(Response.STATUS.FAILURE);
		event.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code());
		event.json(res);
	}
}
