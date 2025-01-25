package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FormationdoctoraleDto {

    private Long id;
    private Long ced;
    private Long etablissement;
    private String axeDeRecherche;
    private String pathImage;
    private String titre;
    private String initiale;
    private Date dateAccreditation;

}
