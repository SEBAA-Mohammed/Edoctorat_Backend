package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for pre-registration request")
public class PreRegisterRequest {

    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "User's last name", example = "Doe", required = true)
    private String nom;

    @Schema(description = "User's first name", example = "John", required = true)
    private String prenom;

    @Schema(description = "Origin URL for email confirmation", example = "http://localhost:4200", required = true)
    private String origin;
}