package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "group_permission")
public class GroupPermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroupModel group;


    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionModel permission;
}
