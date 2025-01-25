package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "commission_professeur")
public class CommissionProfesseurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ProfesseurModel professeur;
    @ManyToOne
    private CommissionModel commission;
}
