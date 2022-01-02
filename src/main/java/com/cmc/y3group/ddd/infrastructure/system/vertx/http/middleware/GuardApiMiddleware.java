package com.cmc.y3group.ddd.infrastructure.system.vertx.http.middleware;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.cmc.y3group.ddd.config.constants.AuthConstant.*;
import static com.cmc.y3group.ddd.infrastructure.support.MappingUtils.OBJ_MAPPER;

@Slf4j
@Component
public class GuardApiMiddleware implements Handler<RoutingContext> {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Transactional
	@Override
	public void handle(RoutingContext event) {
		User user = null;
		String sesApi = event.request().getHeader(SESSION);
		if (!Objects.isNull(sesApi)) {
			String[] splitCookie = sesApi.split("\\|");
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

		if (!Objects.isNull(user)) {
			try {
				String principal = OBJ_MAPPER.writeValueAsString(user);
				JsonObject principalJsonObj = new JsonObject(principal);
				io.vertx.ext.auth.User userVertx = io.vertx.ext.auth.User.create(principalJsonObj);
				event.setUser(userVertx);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage(), e);
			}
		}
		event.next();
	}
}
