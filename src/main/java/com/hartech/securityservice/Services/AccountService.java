package com.hartech.securityservice.Services;

import com.hartech.securityservice.Entities.AppRole;
import com.hartech.securityservice.Entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);

    List<AppUser> listUsers();
}
