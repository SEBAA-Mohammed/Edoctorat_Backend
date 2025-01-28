package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SujetDto {

    private Long id;
    private ProfesseurDto professeur;
    private FormationdoctoraleDto formationDoctorale;
    private String titre;
    private ProfesseurDto codirecteur;
    private String description;
    private Boolean publier;

}
