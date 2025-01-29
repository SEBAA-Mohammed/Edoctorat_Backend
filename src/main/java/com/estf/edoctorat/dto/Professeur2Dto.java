package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professeur2Dto {
    private Long id;
    private String cin;
    private String telProfesseur;
    private String pathPhoto;
    private String grade;
    private String numSom;
    private int nombreEncadre;
    private int nombreProposer;
    private Long etablissement;
}
