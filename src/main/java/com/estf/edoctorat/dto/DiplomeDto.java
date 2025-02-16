package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for diploma/degree information")
public class DiplomeDto {

    @Schema(description = "Unique identifier of the diploma", example = "1")
    private Long id;

    @Schema(description = "Title of the diploma", example = "Bachelor of Computer Science")
    private String intitule;

    @Schema(description = "Type of diploma", example = "BACHELOR")
    private String type;

    @Schema(description = "Date of the commission that awarded the diploma", example = "2023-06-15")
    private String dateCommission;

    @Schema(description = "Mention/Honor received", example = "DISTINCTION")
    private String mention;

    @Schema(description = "Country where the diploma was obtained", example = "Morocco")
    private String pays;

    @Schema(description = "Institution that awarded the diploma", example = "University of Technology")
    private String etablissement;

    @Schema(description = "Field of specialization", example = "Software Engineering")
    private String specialite;

    @Schema(description = "City where the institution is located", example = "Casablanca")
    private String ville;

    @Schema(description = "Province/Region of the institution", example = "Grand Casablanca")
    private String province;

    @Schema(description = "Overall grade average", example = "16.5")
    private double moyen_generale;

    @Schema(description = "List of attached documents related to the diploma")
    private List<AnnexeDto> annexes;
}