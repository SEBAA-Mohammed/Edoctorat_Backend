package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionDto {
    private Long id;
    private CandidatDto candidat;
    private Sujet2Dto sujet;
    private String dateDiposeDossier;
    private String remarque;
    private boolean valider;
    private String pathFile;
}