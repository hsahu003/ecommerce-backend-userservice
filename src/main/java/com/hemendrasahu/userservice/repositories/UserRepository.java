package com.hemendrasahu.userservice.repositories;

import com.hemendrasahu.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> getByEmail(String email);
}
