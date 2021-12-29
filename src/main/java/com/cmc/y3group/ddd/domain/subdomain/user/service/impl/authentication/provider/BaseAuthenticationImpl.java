package com.cmc.y3group.ddd.domain.subdomain.user.service.impl.authentication.provider;

import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserEmailRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserPhoneRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.service.authentication.provider.BaseAuthentication;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.model.email.UserEmail;
import com.cmc.y3group.ddd.domain.subdomain.user.model.phone.UserPhone;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
	public Future<User> authenticate(User user) {
		return Future.future(handle -> {
			User userInStore = userRepository.findByUsername(user.getUsername());

			if (Objects.isNull(userInStore)) {
				UserEmail emailInStore = userEmailRepository.findByEmail(user.getUsername());
				if (!Objects.isNull(emailInStore))
					userInStore = emailInStore.getUser();
				else {
					UserPhone phoneInStore = userPhoneRepository.findByPhone(user.getUsername());
					if (!Objects.isNull(phoneInStore))
						userInStore = phoneInStore.getUser();
				}
			}

			if (!Objects.isNull(userInStore)) {
				boolean verifyPwd = userInStore.verifyPassword(user.getPassword());
				if (verifyPwd) {
					handle.complete(userInStore);
					return;
				}
			}
			handle.fail("Username or password is incorrect.");
		});
	}
}
