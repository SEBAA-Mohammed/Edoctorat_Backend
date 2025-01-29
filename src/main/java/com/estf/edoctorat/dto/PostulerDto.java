package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostulerDto {

    private String pathFile;
    private SujetDto sujet;
    private CandidatDto candidat;
    private Long id;

}
