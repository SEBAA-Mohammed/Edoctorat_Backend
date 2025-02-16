package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.SujetModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for pole-level inscription management")
public class PoleAllInscriptionDto {

    @Schema(description = "Unique identifier of the inscription", example = "1")
    private Long id;

    @Schema(description = "Date when the application dossier was submitted", example = "2024-02-16")
    private Date dateDisposeDossier;

    @Schema(description = "Additional notes or remarks about the inscription", example = "Missing transcript from previous semester")
    private String remarque;

    @Schema(description = "Whether the inscription has been validated", example = "true")
    private boolean valider;

    @Schema(description = "Candidate information")
    private CandidatModel candidat;

    @Schema(description = "Subject/thesis information")
    private SujetModel sujet;
}