package com.cmc.y3group.ddd.app.http.controller.api.user;

import com.cmc.y3group.ddd.app.models.response.Response;
import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.app.models.dto.SignInDTO;
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
public class SignInApi extends Controller {
	public static String PATH = "/user/signin";

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	/** {@inheritDoc} */
	@Override
	public void handle(RoutingContext evt) {
		SignInDTO signInDTO = MappingUtils.requestMapping(evt, SignInDTO.class);

		if (Objects.isNull(signInDTO)) {
			Response res = new Response();
			res.setMessage(HttpResponseStatus.BAD_REQUEST.reasonPhrase());
			res.setCode(HttpResponseStatus.BAD_REQUEST.code());
			res.setStatus(Response.STATUS.FAILURE);
			evt.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
			evt.json(res);
			return;
		}

		UserBuilder builder = UserBuilder.builder();
		builder.setUsername(signInDTO.getUsername());
		builder.setPassword(signInDTO.getPassword());
		builder.setUserService(userService);

		User user = builder.build();
		Future<User> result = user.signin();

		result.onSuccess(signInSuccessUser -> this.handleSuccess(evt, signInSuccessUser));
		result.onFailure(err -> this.handleFailure(evt, err));
	}

	private void handleSuccess(RoutingContext evt, User user) {
		Session ses = new Session();
		ses.setUser(user);
		ses.setSessionService(sessionService);
		ses.generateSessionKey();
		ses.saveChange();
		String cookieSes = user.getId() + "|" + ses.getSession();

		UserResponse userRes = new UserResponse();
		userRes.setId(user.getId());
		userRes.setUsername(user.getUsername());
		userRes.setSession(cookieSes);

		Response res = new Response();
		res.setMessage("Login successfully.");
		res.setCode(Response.CODE.OK);
		res.setStatus(Response.STATUS.SUCCESS);
		res.setData(userRes);
		evt.json(res);
	}

	/**
	 * @param evt Event.
	 * @param throwable Throwable.
	 */
	private void handleFailure(RoutingContext evt, Throwable throwable) {
		Response res = new Response();
		res.setMessage(throwable.getMessage());
		res.setCode(HttpResponseStatus.UNAUTHORIZED.code());
		res.setStatus(Response.STATUS.FAILURE);
		evt.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code());
		evt.json(res);
	}
}
