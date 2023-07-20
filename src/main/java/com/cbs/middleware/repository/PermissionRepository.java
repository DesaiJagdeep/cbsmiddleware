package com.cbs.middleware.repository;

import com.cbs.middleware.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Permission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findAllByObjectAndActionAndRole(String object, String action, String role);
}
