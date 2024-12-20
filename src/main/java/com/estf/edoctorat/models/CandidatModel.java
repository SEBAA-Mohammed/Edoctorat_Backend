package com.estf.edoctorat.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity()
@Data
public class CandidatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cne;
    private String cni;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    private String adresseAr;
    private String adresse;
    private String sexe;
    private String villeDeNaissance;
    private String villeDeNaissanceAr;
    private String ville;
    private Date dateDeNaissance;
    private String typeDeHandicape;
    private String academie;
    private String telCandidat;
    private String pathCv;
    private String pathPhoto;
    private int etatDossier;
    private String situation_familiale;
    private String pay_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    private int fonctionaire;
}
