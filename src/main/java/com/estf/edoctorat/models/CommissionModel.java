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
    private long labo_id;
    @ManyToMany
    @JoinTable(
            name = "commission_professeurs",
            joinColumns = @JoinColumn(name = "professeur_id"),
            inverseJoinColumns = @JoinColumn(name = "commission_id")
    )
    private List<ProfesseurModel> professeurs;
}
