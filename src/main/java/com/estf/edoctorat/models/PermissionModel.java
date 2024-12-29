package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity()
@Data
@Table(name = "permissions")
public class PermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String codename;
}
