package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.*;
import com.estf.edoctorat.services.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Operations", description = "Operations management APIs")
public class OperationsController {

    private final OperationsService operationsService;

    @Operation(summary = "Get all subjects", description = "Retrieves a list of all available subjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/sujets/")
    @PreAuthorize("permitAll()")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSujets() {
        try {
            List<SujetDto> sujets = operationsService.getAllSujets();

            Map<String, Object> response = new HashMap<>();
            response.put("count", sujets.size());
            response.put("results", sujets);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error retrieving subjects");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @Operation(summary = "Get subject by ID", description = "Retrieves a subject by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subject", content = @Content(schema = @Schema(implementation = Sujet2Dto.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    @GetMapping("/sujets/{id}")
    public ResponseEntity<Sujet2Dto> getSujet(
            @Parameter(description = "Subject ID") @PathVariable Long id) {
        return ResponseEntity.ok(operationsService.getSujetById(id));
    }

    @Operation(summary = "Create new subject", description = "Creates a new subject entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject created successfully", content = @Content(schema = @Schema(implementation = Sujet2Dto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/sujets/")
    public ResponseEntity<Sujet2Dto> createSujet(
            @Parameter(description = "Subject details", required = true) @RequestBody CreateSujetDto sujet) {
        return ResponseEntity.ok(operationsService.createSujet(sujet));
    }

    @Operation(summary = "Update subject", description = "Updates an existing subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject updated successfully", content = @Content(schema = @Schema(implementation = Sujet2Dto.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    @PutMapping("/sujets/{id}/")
    public ResponseEntity<Sujet2Dto> updateSujet(
            @Parameter(description = "Subject ID") @PathVariable Long id,
            @Parameter(description = "Updated subject details", required = true) @RequestBody Sujet2Dto sujet) {
        return ResponseEntity.ok(operationsService.updateSujet(id, sujet));
    }

    @Operation(summary = "Delete subject", description = "Deletes a subject by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    @DeleteMapping("/sujets/{id}/")
    public ResponseEntity<Void> deleteSujet(
            @Parameter(description = "Subject ID") @PathVariable Long id) {
        operationsService.deleteSujet(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all commissions", description = "Retrieves a list of all commissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved commissions", content = @Content(schema = @Schema(implementation = CommissionDto.class)))
    })
    @GetMapping("/participant/")
    public ResponseEntity<List<CommissionDto>> getCommissions() {
        return ResponseEntity.ok(operationsService.getAllCommissions());
    }

    @Operation(summary = "Get examination results", description = "Retrieves paginated list of examination results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved results", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/examiners/")
    public ResponseEntity<Map<String, Object>> getResultats(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<ExaminerDto> page = operationsService.getResultats(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get enrolled candidates", description = "Retrieves paginated list of enrolled candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved enrollments", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/inscrits/")
    public ResponseEntity<Map<String, Object>> getMesInscrits(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<InscriptionDto> page = operationsService.getMesInscrits(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }
}