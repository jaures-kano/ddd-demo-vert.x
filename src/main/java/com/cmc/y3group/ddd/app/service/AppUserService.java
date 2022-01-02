package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.FormSignupDTO;
import io.vertx.core.Future;

public interface AppUserService {
	Future<UserResponse> signin(CredentialDTO credentialDTO);
	Future<UserResponse> signup(FormSignupDTO formSignupDTO);
}
