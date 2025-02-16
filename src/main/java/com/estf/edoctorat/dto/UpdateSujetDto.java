package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSujetDto {
    private String titre;
    private String description;
    private String formationDoctoraleId;
    private String coDirecteurId;
}