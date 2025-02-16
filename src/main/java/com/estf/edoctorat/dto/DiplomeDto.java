package com.estf.edoctorat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiplomeDto {
    private Long id;
    private String intitule;
    private String type;
    private String dateCommission;
    private String mention;
    private String pays;
    private String etablissement; 
    private String specialite;
    private String ville;
    private String province;
    private double moyen_generale;
    private List<AnnexeDto> annexes;
}