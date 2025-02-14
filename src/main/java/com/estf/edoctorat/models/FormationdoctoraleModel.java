package com.estf.edoctorat.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "formationdoctorale")
public class FormationdoctoraleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String pathImage;
    private String initiale;
    @Column(nullable = false)
    private String titre;
    @Column(columnDefinition = "LONGTEXT")
    private String axeDeRecherche;
    @Column(columnDefinition = "DATE")
    private Date dateAccreditation;
    @ManyToOne
    @JoinColumn(name = "ced_id")
    @JsonBackReference
    private CedModel ced;
    // @Column(length = 10, nullable = false)
    // private String etablissement_id;
    @ManyToOne
    @JoinColumn(name = "etablissement_id")
    @JsonManagedReference
    private EtablissementModel etablissement;
    @OneToMany(mappedBy = "formationDoctorale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SujetModel> sujets;
}
