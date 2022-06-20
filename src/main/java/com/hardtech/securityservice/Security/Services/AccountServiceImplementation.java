package com.hardtech.securityservice.Security.Services;

import com.hardtech.securityservice.Security.Entities.AppRole;
import com.hardtech.securityservice.Security.Entities.AppUser;
import com.hardtech.securityservice.Security.Repositories.AppRoleRepository;
import com.hardtech.securityservice.Security.Repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


       public AppUser addNewUser(String username, String password, String confirmedPassword) {
        AppUser  user=appUserRepository.findAppUserByUsername(username);
        if(user!=null) throw new RuntimeException("User already exists");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser=new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUserRepository.save(appUser);
        addRoleToUser(username,"USER");
        return appUser;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
        AppUser appUser1 = appUserRepository.save(appUser);
        addRoleToUser(appUser.getUsername(), "USER");
        return appUser1;
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

    public AppUser loadUser(String username){
        return appUserRepository.findAppUserByUsername(username);
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
