package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sujet")

public class SujetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String titre ;
    @Column(nullable = true)
    private String description ;
    private byte publier ;
    @OneToOne
    @JoinColumn(name = "codirecteur_id" , nullable = true)
    private ProfesseurModel codirecteur ;
    @ManyToOne
    @JoinColumn(name = "formationDoctorale_id")
    private FormationdoctoraleModel formationDoctorale ;
    @OneToOne
    @JoinColumn(name = "professeur_id")
    private ProfesseurModel professeur ;
}
