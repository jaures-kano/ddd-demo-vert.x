package com.cmc.y3group.ddd.domain.subdomain.user.model;

import com.cmc.y3group.ddd.domain.subdomain.user.model.email.UserEmail;
import com.cmc.y3group.ddd.domain.subdomain.user.model.phone.UserPhone;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;

import java.util.List;
import java.util.Objects;

public class UserBuilder {
	private String username;
	private String password;
	private List<UserEmail> emails;
	private List<UserPhone> phones;

	private UserService userService;

	private UserBuilder() {
	}

	public UserBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder setEmails(List<UserEmail> emails) {
		this.emails = emails;
		return this;
	}

	public UserBuilder setPhones(List<UserPhone> phones) {
		this.phones = phones;
		return this;
	}

	public UserBuilder setUserService(UserService userService) {
		this.userService = userService;
		return this;
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public User build() {
		assert Objects.isNull(username);
		assert Objects.isNull(password);

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setUserService(userService);
		user.setEmails(emails);
		user.setPhones(phones);
		return user;
	}
}
