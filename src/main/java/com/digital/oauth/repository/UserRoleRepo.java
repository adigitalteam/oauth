package com.digital.oauth.repository;

import com.digital.oauth.entity.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<AuthRole, Long> {
    AuthRole findByRoleNameContaining(String roleName);
}
