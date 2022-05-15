package com.hartech.securityservice.Repositories;

import com.hartech.securityservice.Entities.AppRole;
import com.hartech.securityservice.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByRoleName(String roleName);
}
