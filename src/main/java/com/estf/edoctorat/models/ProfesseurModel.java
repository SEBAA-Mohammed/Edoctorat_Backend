package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Data
@Table(name = "professeur")

public class ProfesseurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, length = 30)
    private String cin;
    @Column(nullable = true, length = 30)
    private String telProfesseur;
    @Column(nullable = true, length = 200)
    private String pathPhoto;
    @Column(nullable = true, length = 10)
    private String grade;
    @Column(nullable = true, length = 50)
    private String numSom;
    @Column(nullable = false, length = 11)
    private int nombreEncadrer;
    private Long itabllisement_id; // to do
    @Column(nullable = true)
    private Long labo_id; // to do
    private Long user_id; // to do
}
