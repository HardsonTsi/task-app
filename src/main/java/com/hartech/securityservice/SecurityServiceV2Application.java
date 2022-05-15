package com.hartech.securityservice;

import com.hartech.securityservice.Security.Entities.AppRole;
import com.hartech.securityservice.Security.Entities.AppUser;
import com.hartech.securityservice.Security.Repositories.AppRoleRepository;
import com.hartech.securityservice.Security.Services.AccountServiceImplementation;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@Transactional
public class SecurityServiceV2Application implements CommandLineRunner {

    final AccountServiceImplementation accountServiceImplementation;
    final AppRoleRepository appRoleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceV2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        //Ajout d'utilisateurs sans rôles
        List.of("Tessi", "Agossou", "Saizonou", "Gouthon", "Avahouin")
                .forEach(appUser -> {
                    accountServiceImplementation.addNewUser(new AppUser(appUser, appUser));
                    System.out.println(appUser + " save avec succes");
                });

        //Ajout de rôles
        List.of("ADMIN", "USER", "CUSTOMER")
                .forEach(appRole -> {
                    accountServiceImplementation.addNewRole(new AppRole(appRole));
                    System.out.println(appRole + " save avec succes");
                });

        //Ajout de rôles aux utilisateurs

        accountServiceImplementation.listUsers()
                .forEach(appUser -> {
                    double random = Math.random() * 10;
                    String roleName = (random >= 0 && random <= 4) ? "ADMIN" : (random >= 5 && random <= 8) ? "CUSTOMER" : "USER";
                    accountServiceImplementation.addRoleToUser(appUser.getUsername(), roleName);
                    System.out.println(roleName + " ajoute a " + appUser.getUsername());
                });


    }
}
