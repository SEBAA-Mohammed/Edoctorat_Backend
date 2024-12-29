package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "formationdoctorale")
public class FormationdoctoraleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    private CedModel ced;
    @Column(length = 10, nullable = false)
    private String etablissement_id;// has relation with etablissement
}
