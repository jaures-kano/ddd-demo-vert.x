package com.cmc.y3group.ddd.domain.subdomain.user.model;

import com.cmc.y3group.ddd.infrastructure.support.Str;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(
	name = "user_sessions",
	indexes = {
		@Index(name = "user_id", columnList = "userId")
	}
)
public class UserSession {
	@Id
	public String id;

	private String agent;
	private String ip;

	private String userId;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;

	// Behavior
	public void generateSession() {
		this.id = Str.randomLower(60);
	}
}
