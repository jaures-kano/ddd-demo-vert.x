package com.cmc.y3group.ddd.domain.subdomain.user.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.user.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionServiceImpl implements SessionService {
	@Autowired
	private SessionRepository sessionService;

	@Override
	public UserSession save(UserSession ses) {
		return sessionService.save(ses);
	}
}
