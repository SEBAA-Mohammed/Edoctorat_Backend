package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commission_professeur")
public class CommissionProfesseurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private ProfesseurModel professeur;
    @ManyToOne
    @JoinColumn(name = "commission_id")
    private CommissionModel commission;

    public CommissionProfesseurModel(ProfesseurModel professeur, CommissionModel commission){
        this.professeur = professeur;
        this.commission = commission;
    }

}
