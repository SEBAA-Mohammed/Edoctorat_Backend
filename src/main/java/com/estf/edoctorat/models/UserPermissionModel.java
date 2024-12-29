package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_permission")
public class UserPermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionModel permission;
}
