package com.cmc.y3group.ddd.domain.subdomain.user.repositories;

import com.cmc.y3group.ddd.domain.subdomain.user.model.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, String> {
}
