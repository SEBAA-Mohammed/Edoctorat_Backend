package com.estf.edoctorat.dto;

import com.estf.edoctorat.models.UserModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for basic professor information")
public class ProfesseurDto {

    @Schema(description = "Unique identifier of the professor", example = "1")
    private Long id;

    @Schema(description = "Professor's last name", example = "Smith")
    private String nom;

    @Schema(description = "Professor's first name", example = "John")
    private String prenom;
}
