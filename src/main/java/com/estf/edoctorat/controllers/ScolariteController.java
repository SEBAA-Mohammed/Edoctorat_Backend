package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.services.ScolariteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Scolarite", description = "APIs for managing student academic records")
@CrossOrigin(origins = "*")
public class ScolariteController {

    private final ScolariteService scolariteService;

    @Operation(summary = "Get all student records", description = "Retrieves a paginated list of all student academic records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student records", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/scolarite/")
    public ResponseEntity<Map<String, Object>> getScolariteCandidats(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<PostulerDto> page = scolariteService.getScolariteCandidats(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update student record", description = "Updates specific fields of a student's academic record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record updated successfully", content = @Content(schema = @Schema(implementation = PostulerDto.class))),
            @ApiResponse(responseCode = "404", description = "Record not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
    @PatchMapping("/scolarite/{id}/")
    public ResponseEntity<PostulerDto> updateScolarite(
            @Parameter(description = "ID of the record to update") @PathVariable Long id,
            @Parameter(description = "Fields to update", required = true) @RequestBody Map<String, Object> updates) {
        return scolariteService.updateScolarite(id, updates);
    }
}