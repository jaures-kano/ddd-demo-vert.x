package com.cmc.y3group.ddd.domain.subdomain.user.factories;

import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserEmail;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserPhone;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class UserBuilder {
	private String id;
	private String name;
	private String password;
	private List<UserEmail> emails;
	private List<UserPhone> phones;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	private UserBuilder() {
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public UserBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public UserBuilder setName(String name) {
		this.name = name;
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

	public UserBuilder setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public UserBuilder setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public UserBuilder addEmail(String email) {
		if (Objects.isNull(email))
			return this;

		if (Objects.isNull(emails))
			this.emails = new ArrayList<>();

		UserEmail userEmail = new UserEmail();
		userEmail.setEmail(email);
		this.emails.add(userEmail);
		return this;
	}

	public UserBuilder addPhoneNumber(String phoneNumber) {
		if (Objects.isNull(phoneNumber))
			return this;

		if (Objects.isNull(phones))
			this.phones = new ArrayList<>();

		UserPhone userPhone = UserPhoneBuilder.builder().setPhoneNumber(phoneNumber).build();
		if (!Objects.isNull(userPhone)) this.phones.add(userPhone);
		return this;
	}

	public User build() {
		User user = new User();
		if (Objects.isNull(this.id))
			this.id = User.generateID();

		user.setId(this.id);
		user.setName(this.name);
		user.setPassword(User.hashPassword(this.password));
		user.setEmails(this.emails);
		user.setPhones(this.phones);

		Timestamp now = Timestamp.from(Instant.now());
		user.setCreatedAt(now);
		user.setUpdatedAt(now);

		if (!Objects.isNull(this.emails)) this.emails.forEach(email -> email.setUserId(user.getId()));
		if (!Objects.isNull(this.phones)) this.phones.forEach(phone -> phone.setUserId(user.getId()));
		return user;
	}
}
