package com.glody.glody_platform.users.repository;

import com.glody.glody_platform.users.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);
}