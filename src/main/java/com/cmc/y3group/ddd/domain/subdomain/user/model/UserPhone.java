package com.cmc.y3group.ddd.domain.subdomain.user.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(
	name = "user_phones",
	indexes = {
		@Index(name = "user_id", columnList = "userId")
	}
)
public class UserPhone {
	public static final String patternPhone = "^(\\+?84|0)([3|5|7|8|9][0-9]{8})$";

	@Id
	private String phone;
	private String iso;
	private String prefix;

	@Column(nullable = false)
	private String userId;
}
