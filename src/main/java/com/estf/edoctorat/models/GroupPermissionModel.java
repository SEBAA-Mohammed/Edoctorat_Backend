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

    private Long groupId;// to do

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionModel permission;
}
