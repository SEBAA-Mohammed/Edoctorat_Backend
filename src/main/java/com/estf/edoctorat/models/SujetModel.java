package com.estf.edoctorat.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "sujet")

public class SujetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    @Column(nullable = true)
    private String description;
    private Boolean publier;
    @OneToOne
    @JoinColumn(name = "codirecteur_id", nullable = true)
    private ProfesseurModel codirecteur;
    @ManyToOne
    @JoinColumn(name = "formationDoctorale_id")
    private FormationdoctoraleModel formationDoctorale;
    @OneToOne
    @JoinColumn(name = "professeur_id")
    private ProfesseurModel professeur;
    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatPostulerModel> candidatPostulers;
    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatNotificationModel> notifications;
}
