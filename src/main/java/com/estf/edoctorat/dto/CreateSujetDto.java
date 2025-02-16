package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for creating a new thesis subject")
public class CreateSujetDto {

    @Schema(description = "Title of the thesis subject", example = "AI Applications in Healthcare", required = true)
    private String titre;

    @Schema(description = "Detailed description of the thesis subject", example = "Research on implementing AI algorithms for early disease detection...", required = true)
    private String description;

    @Schema(description = "ID of the doctoral training program this subject belongs to", example = "1", required = true)
    private Long formationDoctoraleId;

    @Schema(description = "ID of the co-director professor for this thesis", example = "2", required = false)
    private Long coDirecteurId;
}