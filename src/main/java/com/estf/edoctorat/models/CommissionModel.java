package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "commission")
public class CommissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "DATE", nullable = false)
    private Date dateCommission;
    @Column(nullable = false)
    private String lieu;
    @Column(columnDefinition = "TIME(6)", nullable = false)
    private Date heure;
    private long labo_id; //has relation between commission and laboratoire
    @OneToMany(mappedBy = "commission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommissionProfesseurModel> commissionProfesseurs;
}
