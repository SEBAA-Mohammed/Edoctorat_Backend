package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.FormationdoctoraleDto;
import com.estf.edoctorat.mappers.FormationdoctoraleDtoMapper;
import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.services.FormationdoctoraleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formation-doctorale/")
@Tag(name = "Doctoral Training", description = "APIs for managing doctoral training programs")
@CrossOrigin(origins = "*")
public class FormationdoctoraleController {

    @Autowired
    private FormationdoctoraleService formationdoctoraleService;

    @Operation(summary = "Get all doctoral training programs", description = "Retrieves a paginated list of all doctoral training programs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved doctoral training programs", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    // @GetMapping
    // @ResponseBody
    // public ResponseEntity<Map<String, Object>> getAllFormationdoctorale(
    // @Parameter(description = "Number of items per page")
    // @RequestParam(defaultValue = "50") int limit,
    // @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int
    // offset) {

    // Page<FormationdoctoraleModel> listFormationD =
    // formationdoctoraleService.getAll(limit, offset);

    // listFormationD.stream()
    // .map(FormationdoctoraleDtoMapper::toDto)
    // .toList();

    // Map<String, Object> response = new HashMap<>();
    // response.put("count", listFormationD.getTotalElements());
    // response.put("next", listFormationD.hasNext() ? offset + limit : null);
    // response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
    // response.put("results", listFormationD.getContent());
    // return ResponseEntity.ok(response);

    // }
    @GetMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllFormationdoctorale(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<FormationdoctoraleModel> listFormationD = formationdoctoraleService.getAll(limit, offset);

        List<FormationdoctoraleDto> dtoList = listFormationD.getContent().stream()
                .map(FormationdoctoraleDtoMapper::toDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("count", listFormationD.getTotalElements());
        response.put("next", listFormationD.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", dtoList);
        return ResponseEntity.ok(response);
    }

}
