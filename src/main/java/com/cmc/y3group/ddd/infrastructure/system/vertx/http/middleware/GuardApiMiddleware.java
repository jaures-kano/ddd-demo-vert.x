package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import com.cmc.y3group.ddd.domain.subdomain.session.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.*;

@Component
public class GuardApiMiddleware implements Handler<RoutingContext> {
	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public void handle(RoutingContext event) {
		User user = null;
		boolean fail = false;
		String session = event.request().getHeader(SESSION);
		if (!Objects.isNull(session)) {
			String[] sessionDecode = session.split("\\|");
			if (sessionDecode.length == 2) {
				Long idUser = Long.valueOf(sessionDecode[0]);
				String sesUser = sessionDecode[1];

				Session sesModel = sessionRepository.findBySession(sesUser);
				if (!Objects.isNull(sesModel)) {
					user = sesModel.getUser();
					if (!user.getId().equals(idUser)) {
						user = null;
						fail = true;
					}
				}
			}
		}

		if (!Objects.isNull(user)) {
			JsonObject principal = new JsonObject()
				.put(ID, user.getId())
				.put(USERNAME, user.getUsername());
			io.vertx.ext.auth.User userVertx = io.vertx.ext.auth.User.create(principal);
			event.setUser(userVertx);
		}
		event.next();
	}
}
