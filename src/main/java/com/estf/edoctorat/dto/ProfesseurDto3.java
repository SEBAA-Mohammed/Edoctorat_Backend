package com.estf.edoctorat.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ProfesseurDto3 {
    private Long id;
    private String cin;
    private String telProfesseur;
    private String pathPhoto;
    private String grade;
    private String numSom;
    private int nombreEncadre;
    private int nombreProposer;
    private Long etablissement_id;
    private String etablissementNom;
    private long labo_id;
    // User fields
    private String nom;
    private String prenom;
}