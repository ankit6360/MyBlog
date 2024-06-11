package com.myblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.myblog.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}


