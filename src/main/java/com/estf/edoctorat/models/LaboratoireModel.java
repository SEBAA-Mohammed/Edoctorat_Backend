package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "laboratoire")


public class LaboratoireModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nomLaboratoire ;
    @Column(nullable = true)
    private String description ;
    @Column(nullable = true)
    private String pathImage ;
    @Column(nullable = true)
    private String initial ;
    @OneToOne
    @JoinColumn( name = "ced_id")
    private CedModel ced ;
    @OneToOne
    @JoinColumn( name = "directeur_id")
    private ProfesseurModel professeur ;
    @OneToOne
    @JoinColumn( name = "etablissement_id")
    private EtablissementModel etablissement ;
}
