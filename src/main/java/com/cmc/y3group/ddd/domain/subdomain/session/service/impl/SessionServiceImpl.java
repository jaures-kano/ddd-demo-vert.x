package com.cmc.y3group.ddd.domain.subdomain.session.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.session.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import com.cmc.y3group.ddd.domain.subdomain.session.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionServiceImpl implements SessionService {
	@Autowired
	private SessionRepository sessionService;

	@Override
	public void save(Session session) {
		sessionService.save(session);
	}
}
