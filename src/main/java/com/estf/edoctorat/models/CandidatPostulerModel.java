package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Data
@Table(name = "candidat_postuler")
public class CandidatPostulerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true, length = 100)
    private String pathFile;
    private Long candidat_id; // to do
    private Long sujet_id; // to do
}
