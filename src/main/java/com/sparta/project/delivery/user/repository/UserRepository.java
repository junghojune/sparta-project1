package com.sparta.project.delivery.user.repository;

import com.sparta.project.delivery.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
