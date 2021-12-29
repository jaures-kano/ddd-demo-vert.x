package com.cmc.y3group.ddd.domain.subdomain.user.model;

import com.cmc.y3group.ddd.domain.subdomain.user.model.email.UserEmail;
import com.cmc.y3group.ddd.domain.subdomain.user.model.phone.UserPhone;
import com.cmc.y3group.ddd.domain.subdomain.user.service.UserService;
import io.vertx.core.Future;
import io.vertx.ext.auth.HashingStrategy;
import io.vertx.ext.auth.VertxContextPRNG;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@OneToMany(cascade = ALL, mappedBy = "user")
	private List<UserEmail> emails;

	@OneToMany(cascade = ALL, mappedBy = "user")
	private List<UserPhone> phones;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	// Domain
	// Behavior
	private static final HashingStrategy strategy = HashingStrategy.load();
	private transient UserService userService;

	public void changePassword(String newPassword) {
		assert StringUtils.isBlank(newPassword) : "Password is required.";
		this.password = hashPassword(newPassword);
		userService.save(this);
	}

	public Future<User> signin() {
		return userService.signin(this);
	}

	public Future<User> signup() {
		this.password = hashPassword(this.password);

		return Future.future(handle -> {
			userService.signup(this);
			handle.complete(this);
		});
	}

	public Future<User> logout() {
		return null;
	}

	public String hashPassword(String password) {
		return strategy.hash(
			"pbkdf2",
			null,
			VertxContextPRNG.current().nextString(32),
			password
		);
	}

	public boolean verifyPassword(String passwordNeedVerify) {
		return strategy.verify(this.password, passwordNeedVerify);
	}
}
