package com.cmc.y3group.ddd.domain.subdomain.user.service.impl.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.user.dto.CredentialDTO;
import com.cmc.y3group.ddd.domain.subdomain.user.factories.UserPhoneBuilder;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserEmailRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserPhoneRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.BaseAuthentication;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserEmail;
import com.cmc.y3group.ddd.domain.subdomain.user.model.UserPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("baseAuthentication")
@Primary
public class BaseAuthenticationImpl implements BaseAuthentication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserEmailRepository userEmailRepository;

	@Autowired
	private UserPhoneRepository userPhoneRepository;

	@Override
	public User authenticate(CredentialDTO credentialDTO) {
		User user = null;

		Optional<UserEmail> optionalUserEmail = userEmailRepository.findById(credentialDTO.getUsername());
		if (optionalUserEmail.isPresent()) {
			Optional<User> optionalUser = userRepository.findById(optionalUserEmail.get().getUserId());
			if (optionalUser.isPresent())
				user = optionalUser.get();
		} else {
			UserPhone userPhone = UserPhoneBuilder.builder().setPhoneNumber(credentialDTO.getUsername()).build();
			if (!Objects.isNull(userPhone)) {
				Optional<UserPhone> optionalUserPhone = userPhoneRepository.findById(userPhone.getPhone());
				if (optionalUserPhone.isPresent()) {
					Optional<User> optionalUser = userRepository.findById(optionalUserPhone.get().getUserId());
					if (optionalUser.isPresent())
						user = optionalUser.get();
				}
			}
		}

		if (!Objects.isNull(user)) {
			boolean verifyPwd = user.verifyPassword(credentialDTO.getPassword());
			if (verifyPwd) return user;
		}
		return null;
	}
}
