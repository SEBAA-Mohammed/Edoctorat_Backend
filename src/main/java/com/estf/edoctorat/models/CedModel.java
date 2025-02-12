package com.estf.edoctorat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ced")
public class CedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(length = 100)
    private String pathImage;
    private String initiale;
    @Column(nullable = false)
    private String titre;
    @OneToOne
    @JoinColumn(name = "directeur_id")
    @JsonManagedReference
    private ProfesseurModel professeur;
    @OneToMany(mappedBy = "ced", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FormationdoctoraleModel> formationdoctorales;
}
