package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SujetDto {

    private Long id;
    private ProfesseurDto professeur;
    private FormationdoctoraleModel formationDoctorale;
    private String titre;
    private ProfesseurDto codirecteur;
    private String description;
    private boolean publier;


}
