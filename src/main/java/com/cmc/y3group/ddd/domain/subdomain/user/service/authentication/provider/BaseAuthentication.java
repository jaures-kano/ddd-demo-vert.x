package com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;

public interface BaseAuthentication extends Authentication {
	User authenticate(CredentialDTO credentialDTO);
}
