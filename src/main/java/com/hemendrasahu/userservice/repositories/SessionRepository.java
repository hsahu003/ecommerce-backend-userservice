package com.hemendrasahu.userservice.repositories;

import com.hemendrasahu.userservice.models.Session;
import com.hemendrasahu.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    public Optional<Session> getByToken(String token);
}
