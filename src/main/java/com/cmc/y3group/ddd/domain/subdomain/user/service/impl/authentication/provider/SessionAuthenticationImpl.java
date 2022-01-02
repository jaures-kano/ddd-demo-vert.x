package com.cmc.y3group.ddd.domain.subdomain.user.service.impl.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.SessionAuthentication;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionAuthenticationImpl implements SessionAuthentication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Future<User> authenticate(String session) {
		return Future.future(handle -> {
			Optional<UserSession> optionalUserSes = sessionRepository.findById(session);
			if (optionalUserSes.isEmpty()) {
				handle.fail("Session not found.");
				return;
			}

			UserSession userSes = optionalUserSes.get();
			Optional<User> optionalUser = userRepository.findById(userSes.getUserId());
			if (optionalUser.isEmpty()) {
				handle.fail("User not found.");
				return;
			}

			handle.complete(optionalUser.get());
		});
	}
}
