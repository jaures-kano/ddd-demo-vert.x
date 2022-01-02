package com.cmc.y3group.ddd.domain.subdomain.user.model;

import io.vertx.ext.auth.HashingStrategy;
import io.vertx.ext.auth.VertxContextPRNG;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "users")
public class User {
	private static final Long EPOCH = 641300000000L;
	private static final Long RAND = 9999L;
	private static final Long RAND_E = 1000L;

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@OneToMany(cascade = ALL, mappedBy = "userId")
	private List<UserEmail> emails;

	@OneToMany(cascade = ALL, mappedBy = "userId")
	private List<UserPhone> phones;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;

	// Domain
	// Behavior
	private static final HashingStrategy strategy = HashingStrategy.load();

	/**
	 * Ex: 10092982873747
	 */
	public static String generateID() {
		return (Instant.now().toEpochMilli() - EPOCH) +
			String.valueOf((long) ((Math.random() * (RAND - RAND_E)) + RAND_E));
	}

	public void changePassword(String newPwd) {
		this.setPassword(newPwd);
		// TODO: create event change password
	}

	public static String hashPassword(String pwd) {
		return strategy.hash(
			"pbkdf2",
			null,
			VertxContextPRNG.current().nextString(32),
			pwd
		);
	}

	public boolean verifyPassword(String pwdNeedVerify) {
		return strategy.verify(this.password, pwdNeedVerify);
	}
}
