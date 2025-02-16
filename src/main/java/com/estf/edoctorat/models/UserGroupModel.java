package com.estf.edoctorat.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity()
@Data
@Table(name = "user_group")

public class UserGroupModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UserModel> users;

    @OneToMany(mappedBy = "userGroup", fetch = FetchType.EAGER)
    private List<AuthGroupModel> groups;
}
