package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for thesis subject pre-selection results")
public class PreselectionParSujetDto {

    @Schema(description = "Unique identifier of the examination record", example = "1")
    private Long examiner_id;

    @Schema(description = "Candidate information")
    private CandidatDto candidat;

    @Schema(description = "Whether the candidate has been pre-selected", example = "true")
    private Boolean valider;
}
