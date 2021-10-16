package com.digital.oauth.repository;

import com.digital.oauth.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<UserPermission,Long> {
    Optional<UserPermission> findUserPermissionByUserIdAndAndPermissionId(Long userId,Long permissionId);
}
