package com.estf.edoctorat.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CandidatDto {
    private Long id;
    private String cne;
    private String pays;
    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    private String adresse;
    private String adresseAr;
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
    private Integer etatDossier;
    private String situation_familiale;
    private Boolean fonctionnaire;
}