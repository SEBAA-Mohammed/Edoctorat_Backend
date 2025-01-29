package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.models.ProfesseurModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sujet2Dto {
    private Long id;
    private ProfesseurModel professeur;
    private ProfesseurModel coDirecteur;
    private String titre;
    private String description;
    private FormationdoctoraleModel formationDoctorale;
    private boolean publier;
}