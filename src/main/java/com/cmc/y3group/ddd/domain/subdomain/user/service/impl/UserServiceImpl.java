package com.cmc.y3group.ddd.domain.subdomain.user.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.BaseAuthentication;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BaseAuthentication baseAuthentication;

	@Override
	public Future<User> signin(User user) {
		return Future.future(handle -> {
			Future<User> fut1 = baseAuthentication.authenticate(user);
			fut1.onSuccess(handle::complete);
			fut1.onFailure(errBasic -> handle.fail(errBasic.getMessage()));
		});
	}

	@Override
	public User signup(User user) {
		return userRepository.save(user);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}
