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
    private String titre;
    private String description;
    private Byte publier;
    private ProfesseurDto codirecteur;
    private FormationdoctoraleModel formationDoctorale;
    private ProfesseurDto professeur;


}
