package com.hartech.securityservice.Security.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hartech.securityservice.Security.Entities.AppRole;
import com.hartech.securityservice.Security.Entities.AppUser;
import com.hartech.securityservice.Security.Services.AccountServiceImplementation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImplementation accountService;

    @GetMapping("/users")
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }

    @PostMapping("/users")
    public AppUser addUser(@RequestBody AppUser appUser) {
        return accountService.addNewUser(appUser);
    }

    @PostMapping("roles")
    public AppRole appRoles(@RequestBody AppRole appRole) {
        return accountService.addNewRole(appRole);
    }

    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleForm form) {
        accountService.addRoleToUser(form.getUsername(), form.getRoleName());
        return new ResponseEntity<>(form.getRoleName() + " ajoute a " + form.getUsername(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authToken = request.getHeader("Authorization");

        if (authToken != null && authToken.startsWith("Bearer ")) {
            try {
                String refreshToken = authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("mySecret123");
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                AppUser appUser = accountService.loadUser(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getAppUserRoles().stream().map(grantedAuthorithy -> grantedAuthorithy.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access-token", jwtAccessToken);
                tokens.put("refresh-token", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("Refresh Token required");
        }
    }

}

@Data
class RoleForm {
    private String username;
    private String roleName;
}
