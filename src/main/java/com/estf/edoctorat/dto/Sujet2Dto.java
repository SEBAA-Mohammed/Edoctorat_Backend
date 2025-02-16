package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for thesis subject information")
public class Sujet2Dto {

    @Schema(description = "Unique identifier of the subject", example = "1")
    private Long id;

    @Schema(description = "Professor supervising the thesis")
    @JsonManagedReference
    private ProfesseurModel professeur;

    @Schema(description = "Co-director of the thesis")
    @JsonManagedReference
    private ProfesseurModel coDirecteur;

    @Schema(description = "Title of the thesis subject", example = "AI Applications in Healthcare")
    private String titre;

    @Schema(description = "Detailed description of the thesis subject", example = "Research on implementing AI algorithms for early disease detection...")
    private String description;

    @Schema(description = "Associated doctoral training program")
    @JsonBackReference
    private FormationdoctoraleModel formationDoctorale;

    @Schema(description = "Whether the subject has been published", example = "true")
    private boolean publier;
}