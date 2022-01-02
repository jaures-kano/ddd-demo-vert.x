package com.cmc.y3group.ddd.domain.subdomain.user.service;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;

public interface SessionService {
	UserSession save(UserSession session);
}
