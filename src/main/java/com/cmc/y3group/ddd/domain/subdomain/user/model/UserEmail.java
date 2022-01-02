package com.cmc.y3group.ddd.domain.subdomain.user.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(
	name = "user_emails",
	indexes = {
		@Index(name = "user_id", columnList = "userId")
	}
)
public class UserEmail {
	@Id
	private String email;

	@Column(nullable = false)
	private String userId;
}
