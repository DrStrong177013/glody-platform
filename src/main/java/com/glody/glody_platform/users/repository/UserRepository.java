package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
    List<User> findAllByIsDeletedFalse(Sort sort);
    Page<User> findAllByIsDeletedFalse(Pageable pageable);
    Optional<User> findByEmail(String email);



}