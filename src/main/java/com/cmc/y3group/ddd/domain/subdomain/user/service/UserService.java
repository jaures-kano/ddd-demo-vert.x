package com.cmc.y3group.ddd.domain.subdomain.user.service;

import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;

public interface UserService {
	User signin(CredentialDTO credentialDTO);
	User signup(User user);
	User save(User user);
}
