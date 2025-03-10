package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for professor information")
public class Professeur2Dto {

    @Schema(description = "Unique identifier of the professor", example = "1")
    private Long id;

    @Schema(description = "National ID number", example = "AB123456")
    private String cin;

    @Schema(description = "Professor's phone number", example = "+212600000000")
    private String telProfesseur;

    @Schema(description = "Path to professor's photo", example = "/uploads/photos/prof123.jpg")
    private String pathPhoto;

    @Schema(description = "Academic grade/rank", example = "PROFESSOR")
    private String grade;

    @Schema(description = "Professor's registration number", example = "P12345")
    private String numSom;

    @Schema(description = "Number of students currently supervised", example = "5")
    private int nombreEncadre;

    @Schema(description = "Number of subjects proposed", example = "3")
    private int nombreProposer;

    @Schema(description = "ID of the institution where professor teaches", example = "1")
    private Long etablissement;
}
