package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for candidate applications to thesis subjects")
public class PostulerDto {

    @Schema(description = "Path to the uploaded application file", example = "/uploads/applications/2024/user123.pdf")
    private String pathFile;

    @Schema(description = "Subject being applied for")
    private SujetDto sujet;

    @Schema(description = "Candidate information")
    private CandidatDto candidat;

    @Schema(description = "Unique identifier of the application", example = "1")
    private Long id;
}
