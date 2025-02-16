package com.estf.edoctorat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for user login credentials")
public class LoginRequest {

    @Schema(description = "Username or email for authentication", example = "john.doe@example.com", required = true)
    private String username;

    @Schema(description = "User's password", example = "password123", required = true, minLength = 8)
    private String password;
}
