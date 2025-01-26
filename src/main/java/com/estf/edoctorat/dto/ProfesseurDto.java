package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProfesseurDto {
    private Long id;
    private String nom;
    private String prenom;
}
