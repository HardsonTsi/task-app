package com.hardtech.securityservice.Security.Services;

import com.hardtech.securityservice.Security.Entities.AppRole;
import com.hardtech.securityservice.Security.Entities.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);

    AppUser addNewUser(String username, String password, String confirmedPassword);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUser(String username);

    UserDetails loadUserByUsername(String username);

    List<AppUser> listUsers();
}
