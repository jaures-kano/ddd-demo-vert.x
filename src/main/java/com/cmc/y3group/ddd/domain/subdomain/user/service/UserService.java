package com.cmc.y3group.ddd.domain.subdomain.user.service;

import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;

public interface UserService {
	Future<User> signin(User user);
	User signup(User user);
	User save(User user);
}
