package com.estf.edoctorat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.estf.edoctorat.dto.CalendrierDto;
import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.services.DirecteurPoleCalendrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@Tag(name = "Calendrier", description = "Calendar management APIs")
@CrossOrigin(origins = "*")
public class CalendrierController {

    @Autowired
    private DirecteurPoleCalendrierService directeurPoleCalendrierService;

    @Operation(summary = "Get all calendar entries", description = "Retrieves a paginated list of all calendar entries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calendar entries", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-calendrier/")
    public ResponseEntity<Map<String, Object>> index(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<DirecteurPoleCalendrierModel> resultats = directeurPoleCalendrierService.getAll(limit, offset);
        Map<String, Object> response = new HashMap<>();
        response.put("count", resultats.getTotalElements());
        response.put("next", resultats.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", resultats);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update calendar entry", description = "Update the start and end dates of a specific calendar entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calendar entry updated successfully", content = @Content(schema = @Schema(implementation = DirecteurPoleCalendrierModel.class))),
            @ApiResponse(responseCode = "404", description = "Calendar entry not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PatchMapping("/update-calendrier/{calendrierId}/")
    public ResponseEntity<DirecteurPoleCalendrierModel> updateCalendrier(
            @Parameter(description = "ID of the calendar entry to update") @PathVariable int calendrierId,
            @Parameter(description = "Calendar update details", required = true) @RequestBody CalendrierDto updateRequest) {
        Optional<DirecteurPoleCalendrierModel> updatedCalendrier = directeurPoleCalendrierService
                .updateCalendrier(calendrierId, updateRequest.getDateDebut(), updateRequest.getDateFin());

        return updatedCalendrier
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
