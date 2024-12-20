package com.estf.edoctorat.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity()
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private Date last_login;
    private Boolean is_superuser;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private Boolean is_staff;
    private Boolean is_active;
    private Date date_joined;
}
