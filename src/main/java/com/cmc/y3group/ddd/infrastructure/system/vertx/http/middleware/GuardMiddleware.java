package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import com.cmc.y3group.ddd.domain.subdomain.user.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.vertx.core.Handler;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.*;
import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class GuardMiddleware implements Handler<RoutingContext> {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public void handle(RoutingContext ctx) {
		io.vertx.ext.web.Session ses = ctx.session();
		String id = ses.get(ID);

		User user = null;
		io.vertx.ext.auth.User userVertx = null;
		if (!Objects.isNull(id)) {
			Optional<User> optionalUser = userRepository.findById(id);
			if (optionalUser.isPresent())
				user = optionalUser.get();
		}

		if (Objects.isNull(user)) {
			Cookie cookieOfSes = ctx.request().getCookie(SESSION);
			if (!Objects.isNull(cookieOfSes)) {
				String sesWeb = cookieOfSes.getValue();
				String[] splitCookie = sesWeb.split("\\|");
				if (splitCookie.length == 2) {
					String userId = splitCookie[0];
					String userSes = splitCookie[1];

					Optional<UserSession> optionalUserSes = sessionRepository.findById(userSes);
					if (optionalUserSes.isPresent()) {
						Optional<User> optionalUser = userRepository.findById(optionalUserSes.get().getUserId());
						if (optionalUser.isPresent() && optionalUser.get().getId().equals(userId))
							user = optionalUser.get();
					}
				}
			}
		}
		if (!Objects.isNull(user)) {
			try {
				String json = OBJ_MAPPER.writeValueAsString(user);
				JsonObject principal = new JsonObject(json);
//					.put(ID, user.getId());
				userVertx = io.vertx.ext.auth.User.create(principal);
				ctx.setUser(userVertx);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage(), e);
			}
		}
		ctx.next();
	}
}
