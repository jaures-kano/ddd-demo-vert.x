package com.cmc.y3group.ddd.domain.subdomain.user.factories;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class UserSessionBuilder {
	private String id;
	private String agent;
	private String ip;

	private String userId;

	private UserSessionBuilder() {
	}

	public static UserSessionBuilder bulder() {
		return new UserSessionBuilder();
	}

	public UserSessionBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public UserSessionBuilder setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public UserSessionBuilder setAgent(String agent) {
		this.agent = agent;
		return this;
	}

	public UserSessionBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public UserSession build() {
		UserSession ses = new UserSession();
		if (Objects.isNull(this.id))
			ses.generateSession();
		else
			ses.setId(this.id);

		ses.setUserId(this.userId);
		ses.setIp(this.ip);
		ses.setAgent(this.agent);
		ses.setUserId(this.userId);

		Timestamp now = Timestamp.from(Instant.now());
		ses.setCreatedAt(now);
		ses.setUpdatedAt(now);
		return ses;
	}
}
