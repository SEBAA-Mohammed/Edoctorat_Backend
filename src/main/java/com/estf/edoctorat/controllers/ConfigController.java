package com.estf.edoctorat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.estf.edoctorat.models.ConfigModel;
import com.estf.edoctorat.services.ConfigService;

@RestController
@RequestMapping("/api")
@Tag(name = "Configuration", description = "APIs for managing application configuration")
@CrossOrigin(origins = "*")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Operation(summary = "Get base configuration", description = "Retrieves the base configuration settings for the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved configuration", content = @Content(schema = @Schema(implementation = ConfigModel.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-base-config/")
    public ResponseEntity<ConfigModel> getBaseConfig() {
        return ResponseEntity.ok(configService.getBaseConfig());
    }
}