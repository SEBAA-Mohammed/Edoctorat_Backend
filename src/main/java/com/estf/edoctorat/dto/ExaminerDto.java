package com.estf.edoctorat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExaminerDto {

    private Long id;
    private SujetDto sujet;
    private String cne;
    private float noteDossier;
    private float noteEntretien;
    private String decision;
    private Long commission;
    private CandidatDto candidat;
    private Boolean publier;

}
