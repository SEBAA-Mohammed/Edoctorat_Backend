package com.estf.edoctorat.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity()
@Data
@Table(name = "user")

public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private Date last_login;
    private Boolean is_superuser;
    private String username;
    private String first_name;  // prenom
    private String last_name;   // nom
    private String email;
    private Boolean is_staff;
    private Boolean is_active;
    private Date date_joined;
    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroupModel userGroup;
    
    @OneToOne(mappedBy = "user")
    private CandidatModel candidat;
    
    @OneToOne(mappedBy = "user") 
    private ProfesseurModel professeur;

}
