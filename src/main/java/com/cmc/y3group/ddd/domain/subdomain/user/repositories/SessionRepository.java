package com.cmc.y3group.ddd.domain.subdomain.user.repositories;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<UserSession, String> {
}
