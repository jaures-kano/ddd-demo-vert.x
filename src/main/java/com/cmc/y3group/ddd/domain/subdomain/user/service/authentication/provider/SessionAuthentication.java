package com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;

public interface SessionAuthentication extends Authentication {
	Future<User> authenticate(String session);
}
