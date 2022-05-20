package com.hartech.securityservice.Security.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Attempt Authentication");
        System.out.println("======================");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("Successfull Authentication");
        System.out.println("======================");
        User user = (User) authResult.getPrincipal();
        List<String> roles=new ArrayList<>();
        authResult.getAuthorities().forEach(a-> roles.add(a.getAuthority()));
        //Generation du Token
        Algorithm algorithm = Algorithm.HMAC256("mySecret123");
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                //5minutes
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", Arrays.asList(roles.toArray(new String[roles.size()])))
                .sign(algorithm);

        response.setHeader("Authorization", jwtAccessToken);
        System.out.println(jwtAccessToken);
    }
}
