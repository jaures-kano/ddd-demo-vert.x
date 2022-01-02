package com.cmc.y3group.ddd.domain.subdomain.user.factories;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserPhone;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class UserPhoneBuilder {
	private String phoneNumber;

	private UserPhoneBuilder() {
	}

	public static UserPhoneBuilder builder() {
		return new UserPhoneBuilder();
	}

	public UserPhoneBuilder setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserPhone build() {
		UserPhone userPhone = new UserPhone();
		Pattern pattern = Pattern.compile(UserPhone.patternPhone);
		Matcher matcher = pattern.matcher(phoneNumber);

		if (matcher.find()) {
			userPhone.setPrefix(matcher.group(1));
			userPhone.setPhone(matcher.group(2));
		} else {
			// TODO: exception ...
			return null;
		}

		return userPhone;
	}
}
