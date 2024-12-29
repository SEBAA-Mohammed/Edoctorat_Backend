package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Data
@Table(name = "professeur_laboratoir")

public class ProfesseurLaboratoirModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String nomLaboratoire;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String pathImage;
    @Column(nullable = true, length = 255)
    private String initial;
    private Long ced_id; // to do
    private Long directeur_id; // to do
    private Long etablissement_id; // to do
}
