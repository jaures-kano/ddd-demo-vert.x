package com.cmc.y3group.ddd.domain.subdomain.user.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.BaseAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BaseAuthentication baseAuthentication;

	@Override
	public User signin(CredentialDTO credentialDTO) {
		return baseAuthentication.authenticate(credentialDTO);
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
