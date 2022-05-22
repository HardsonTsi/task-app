package com.hartech.securityservice.Security.Services;

import com.hartech.securityservice.Security.Entities.AppRole;
import com.hartech.securityservice.Security.Entities.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUser(String username);

    UserDetails loadUserByUsername(String username);

    List<AppUser> listUsers();
}
