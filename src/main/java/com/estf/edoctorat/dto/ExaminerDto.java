package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for examination results")
public class ExaminerDto {

    @Schema(description = "Unique identifier of the examination", example = "1")
    private Long id;

    @Schema(description = "Subject being examined")
    private SujetDto sujet;

    @Schema(description = "National Student ID number", example = "H123456")
    private String cne;

    @Schema(description = "Score for candidate's application file", example = "16.5")
    private float noteDossier;

    @Schema(description = "Score for candidate's interview", example = "17.0")
    private float noteEntretien;

    @Schema(description = "Final decision on the candidate", example = "ACCEPTED")
    private String decision;

    @Schema(description = "ID of the examining commission", example = "1")
    private Long commission;

    @Schema(description = "Candidate information")
    private CandidatDto candidat;

    @Schema(description = "Whether the results have been published", example = "true")
    private Boolean publier;
}
