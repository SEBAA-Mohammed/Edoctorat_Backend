package com.estf.edoctorat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference // Prevents serialization of this side
    private ProfesseurModel professeur;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    @JsonManagedReference // Serializes this side
    private CommissionModel commission;

    public CommissionProfesseurModel(ProfesseurModel professeur, CommissionModel commission) {
        this.professeur = professeur;
        this.commission = commission;
    }
}
