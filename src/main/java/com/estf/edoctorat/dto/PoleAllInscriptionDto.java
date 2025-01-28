package com.estf.edoctorat.dto;


import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.SujetModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoleAllInscriptionDto {
    private Long id;
    private Date dateDisposeDossier;
    private String remarque;
    private boolean valider;
    private CandidatModel candidat;
    private SujetModel sujet;
}