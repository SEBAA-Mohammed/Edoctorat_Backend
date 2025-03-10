package com.estf.edoctorat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "professeur")
public class ProfesseurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String cin;
    @Column(length = 30)
    private String telProfesseur;
    @Column(length = 200)
    private String pathPhoto;
    @Column(length = 10)
    private String grade;
    @Column(length = 50)
    private String numSom;
    @Column(nullable = false)
    private int nombreEncadre;
    @Column(nullable = false)
    private int nombreProposer;
    // @Column(length = 10, nullable = false)
    // private String etablissement_id;
    @ManyToOne
    @JoinColumn(name = "etablissement_id")
    private EtablissementModel etablissement;
    @Column(nullable = false)
    private long labo_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserModel user;
    @OneToMany(mappedBy = "professeur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CommissionProfesseurModel> commissionProfesseurs;
    @OneToOne(mappedBy = "professeur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private CedModel ced;
}
