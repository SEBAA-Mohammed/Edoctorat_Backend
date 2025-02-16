package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for doctoral program registration/inscription")
public class InscriptionDto {

    @Schema(description = "Unique identifier of the inscription", example = "1")
    private Long id;

    @Schema(description = "Candidate information")
    private CandidatDto candidat;

    @Schema(description = "Thesis subject information")
    private Sujet2Dto sujet;

    @Schema(description = "Date when the application dossier was submitted", example = "2024-02-15")
    private String dateDiposeDossier;

    @Schema(description = "Additional notes or remarks about the inscription", example = "Missing transcript from previous institution")
    private String remarque;

    @Schema(description = "Whether the inscription has been validated", example = "true")
    private boolean valider;

    @Schema(description = "Path to the uploaded inscription file", example = "/uploads/inscriptions/2024/user123.pdf")
    private String pathFile;
}