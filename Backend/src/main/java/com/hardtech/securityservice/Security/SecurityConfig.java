package com.hardtech.securityservice.Security;

import com.hardtech.securityservice.Security.Filter.JwtAuthenticationFilter;
import com.hardtech.securityservice.Security.Filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    //Specifier les utilisateurs qui ont droit d'accès
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    //Specifier les accès
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();
        //Ne pas utiliser les sessions
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/login/**", "/signup/**", "/refreshToken/**").permitAll();

        //Taks
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/tasks/all/**", "tasks/{id}/**", "/tasks/search/**").hasAnyAuthority("ADMIN", "USER", "CUSTOMER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/save/**").hasAnyAuthority("ADMIN", "USER", "CUSTOMER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/tasks/maketask/**", "tasks/update/**").hasAnyAuthority("ADMIN", "USER", "CUSTOMER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/tasks/alltasks/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/tasks/delete/{id}/**").hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/addRoleToUser/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
