package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "inscription")
public class InscriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(nullable = true)
    private Date dateDisposeDossier ;
    @Column(nullable = true)
    private String remarque ;
    private byte valider ;
    @OneToOne
    @JoinColumn(name = "candidat_id")
    private CandidatModel candidat ;
    @OneToOne
    @JoinColumn(name = "sujet_id")
    private SujetModel sujet ;
}
