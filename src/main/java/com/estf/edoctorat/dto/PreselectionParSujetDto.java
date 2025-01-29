package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PreselectionParSujetDto {

    private Long examiner_id;
    private CandidatDto candidat;
    private Boolean valider;

}
