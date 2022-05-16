package com.hartech.securityservice.Security.Controllers;

import com.hartech.securityservice.Security.Entities.AppUser;
import com.hartech.securityservice.Security.Services.AccountServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImplementation accountService;

    @GetMapping("/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }


}
