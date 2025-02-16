package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.Sujet2Dto;
import com.estf.edoctorat.mappers.SujetMapper;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.services.PoleSujetService;
import com.estf.edoctorat.services.SujetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

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

@RestController
@RequestMapping("/api")
@Tag(name = "Pole Subjects", description = "APIs for managing subjects at pole level")
@CrossOrigin(origins = "*")
public class PoleSujetController {
    @Autowired
    private PoleSujetService sujetService;

    @Operation(summary = "Get all subjects", description = "Retrieves a paginated list of all subjects at pole level")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-all-sujets/")
    public ResponseEntity<Map<String, Object>> getAllSujets(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<SujetModel> page = sujetService.getAllSujets(limit, offset);
        List<Sujet2Dto> sujetDtos = SujetMapper.toDtoList(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", sujetDtos);

        return ResponseEntity.ok(response);
    }
}