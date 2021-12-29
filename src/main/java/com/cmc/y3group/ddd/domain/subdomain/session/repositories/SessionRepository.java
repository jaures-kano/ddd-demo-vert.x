package com.cmc.y3group.ddd.domain.subdomain.session.repositories;

import com.cmc.y3group.ddd.domain.subdomain.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
	Session findBySession(String session);
}
