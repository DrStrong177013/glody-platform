package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
}