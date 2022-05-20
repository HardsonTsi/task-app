package com.hartech.securityservice.Security.Services;

import com.hartech.securityservice.Security.Entities.AppRole;
import com.hartech.securityservice.Security.Entities.AppUser;
import com.hartech.securityservice.Security.Repositories.AppRoleRepository;
import com.hartech.securityservice.Security.Repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImplementation implements AccountService, UserDetailsService {

    final AppUserRepository appUserRepository;
    final AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);

        if (appUser.getAppUserRoles().contains(appRole)) {
            throw new RuntimeException("Role existant");
        } else {
            appUser.getAppUserRoles().add(appRole);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findAppUserByUsername(username);
        if (user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Not found");
        }else{
            System.out.println("Not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAppUserRoles().forEach(r-> authorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }


}
