package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSujetDto {
    private String titre;
    private String description;
    private Long formationDoctoraleId;
    private Long coDirecteurId;
}