package com.hartech.securityservice.Security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@DynamicUpdate
@ToString
public class AppUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> appUserRoles = new ArrayList<>();

    //Constructeur utilisé juste pour le test
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
