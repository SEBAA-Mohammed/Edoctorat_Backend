package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for thesis subject information")
public class SujetDto {

    @Schema(description = "Unique identifier of the subject", example = "1")
    private Long id;

    @Schema(description = "Professor supervising the thesis")
    private ProfesseurDto professeur;

    @Schema(description = "Associated doctoral training program")
    private FormationdoctoraleDto formationDoctorale;

    @Schema(description = "Title of the thesis subject", example = "AI Applications in Healthcare")
    private String titre;

    @Schema(description = "Co-director of the thesis")
    private ProfesseurDto coDirecteur;

    @Schema(description = "Detailed description of the thesis subject", example = "Research on implementing AI algorithms for early disease detection...")
    private String description;

    @Schema(description = "Whether the subject has been published", example = "true")
    private Boolean publier;
}
