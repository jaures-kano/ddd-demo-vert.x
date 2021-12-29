package com.cmc.y3group.ddd.domain.subdomain.user.service.impl.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import com.cmc.y3group.ddd.domain.subdomain.session.repositories.SessionRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.SessionAuthentication;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SessionAuthenticationImpl implements SessionAuthentication {
	@Autowired
	private SessionRepository sessionRepository;

	/** {@inheritDoc} */
	@Override
	public Future<User> authenticate(String sessions) {
		return Future.future(handle -> {
			Session sesSearched = sessionRepository.findBySession(sessions);
			if (Objects.isNull(sesSearched)) {
				handle.fail("Session not found.");
				return;
			}
			User user = sesSearched.getUser();
			handle.complete(user);
		});
	}
}
