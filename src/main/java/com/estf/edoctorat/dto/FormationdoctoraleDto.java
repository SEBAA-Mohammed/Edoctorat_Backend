package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for doctoral training program information")
public class FormationdoctoraleDto {

    @Schema(description = "Unique identifier of the doctoral training program", example = "1")
    private Long id;

    @Schema(description = "ID of the associated Doctoral Studies Center (CED)", example = "1")
    private Long ced;

    @Schema(description = "ID of the institution offering the program", example = "1")
    private Long etablissement;

    @Schema(description = "Research axis/focus of the program", example = "Computer Science and AI")
    private String axeDeRecherche;

    @Schema(description = "Path to the program's logo/image", example = "/images/programs/cs.jpg")
    private String pathImage;

    @Schema(description = "Full title of the doctoral program", example = "Computer Science and Artificial Intelligence")
    private String titre;

    @Schema(description = "Program initials/abbreviation", example = "CSAI")
    private String initiale;

    @Schema(description = "Date when the program was accredited", example = "2023-09-01")
    private Date dateAccreditation;
}
