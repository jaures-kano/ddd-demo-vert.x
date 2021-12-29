package com.cmc.y3group.ddd.domain.subdomain.session.model;

import com.cmc.y3group.ddd.domain.subdomain.session.service.SessionService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.infrastructure.support.Str;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessions")
public class Session {
	@Id
	private String session;

	@OneToOne
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	// Domain
	// Behavior
	private transient SessionService sessionService;

	public void generateSessionKey() {
		this.session = Str.randomLower(60);
	}

	public void saveChange(){
		sessionService.save(this);
	}
}
