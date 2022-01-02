package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.response.user.UserResponse;
import com.cmc.y3group.ddd.app.service.AppUserService;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.factories.UserSessionBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import com.cmc.y3group.ddd.domain.subdomain.user.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.user.dto.FormSignupDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.factories.UserBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Override
	public Future<UserResponse> signin(CredentialDTO credentialDTO) {
		return Future.future(promise -> {
			User user = userService.signin(credentialDTO);
			if (Objects.isNull(user)) {
				promise.fail("Incorrect username or password.");
				return;
			}
			UserSessionBuilder sesBuilder = UserSessionBuilder.bulder()
				.setUserId(user.getId())
				.setIp(credentialDTO.getIp())
				.setAgent(credentialDTO.getAgent());

			UserSession userSes = sessionService.save(sesBuilder.build());

			String ses = user.getId() + "|" + userSes.getId();

			UserResponse userRes = new UserResponse();
			userRes.setUserId(user.getId());
			userRes.setName(user.getName());
			userRes.setSession(ses);
			promise.complete(userRes);
		});
	}

	@Override
	public Future<UserResponse> signup(FormSignupDTO formSignupDTO) {
		return Future.future(promise -> {
			UserBuilder builder = UserBuilder.builder();
			builder.setName(formSignupDTO.getName());
			builder.addEmail(formSignupDTO.getEmail());
			builder.addPhoneNumber(formSignupDTO.getPhone());
			builder.setPassword(formSignupDTO.getPassword());
			User user = builder.build();
			user = userService.signup(user);

			UserSessionBuilder sesBuilder = UserSessionBuilder.bulder();
			sesBuilder.setUserId(user.getId());
			sesBuilder.setIp(formSignupDTO.getIp());
			sesBuilder.setAgent(formSignupDTO.getAgent());

			UserSession userSes = sessionService.save(sesBuilder.build());

			String ses = user.getId() + "|" + userSes.getId();

			UserResponse userRes = new UserResponse();
			userRes.setUserId(user.getId());
			userRes.setName(user.getName());
			userRes.setSession(ses);
			promise.complete(userRes);
		});
	}
}
