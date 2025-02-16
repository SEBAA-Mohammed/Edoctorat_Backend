package com.estf.edoctorat.dto;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Authentication Response containing tokens and user information")
public class AuthResponse {
    @Schema(
        description = "JWT access token for API authentication",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        required = true
    )
    private String access;

    @Schema(
        description = "JWT refresh token for getting new access tokens",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        required = true
    )
    private String refresh;

    @Schema(
        description = "User information containing profile details",
        required = true
    )
    private UserInfo userInfo;
}
