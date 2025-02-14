package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sujet2Dto {
    private Long id;
    @JsonManagedReference
    private ProfesseurModel professeur;
    @JsonManagedReference
    private ProfesseurModel coDirecteur;
    private String titre;
    private String description;
    @JsonBackReference
    private FormationdoctoraleModel formationDoctorale;
    private boolean publier;
}