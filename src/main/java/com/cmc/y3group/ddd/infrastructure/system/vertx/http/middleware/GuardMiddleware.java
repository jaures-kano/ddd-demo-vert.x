package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import com.cmc.y3group.ddd.domain.subdomain.session.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.*;

@Component
public class GuardMiddleware implements Handler<RoutingContext> {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public void handle(RoutingContext event) {
		io.vertx.ext.web.Session ses = event.session();
		Long id = ses.get(ID);

		User user = null;
		io.vertx.ext.auth.User userVertx = null;
		if (!Objects.isNull(id)) {
			Optional<User> optionalUser = userRepository.findById(id);
			if (optionalUser.isPresent())
				user = optionalUser.get();
		}

		if (Objects.isNull(user)) {
			Cookie cookieOfSes = event.request().getCookie(SESSION);
			if (!Objects.isNull(cookieOfSes)) {
				String sesWeb = cookieOfSes.getValue();
				String[] splitCookie = sesWeb.split("\\|");
				if (splitCookie.length == 2) {
					Long idUser = Long.valueOf(splitCookie[0]);
					String sesUser = splitCookie[1];

					Session sesModel = sessionRepository.findBySession(sesUser);
					if (Objects.isNull(sesModel)) {
						// TODO: incrementally the number of failed logins...
					} else user = sesModel.getUser();
				}
			}
		}
		if (!Objects.isNull(user)) {
			JsonObject principal = new JsonObject()
				.put(ID, user.getId())
				.put(USERNAME, user.getUsername());
			userVertx = io.vertx.ext.auth.User.create(principal);
			event.setUser(userVertx);
		}
		event.next();
	}
}
