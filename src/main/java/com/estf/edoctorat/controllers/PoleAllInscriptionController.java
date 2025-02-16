package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PoleAllInscriptionDto;
import com.estf.edoctorat.mappers.PoleAllInscriptionDtoMapper;
import com.estf.edoctorat.models.InscriptionModel;
import com.estf.edoctorat.services.PoleAllInscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Pole Inscriptions", description = "APIs for managing all pole inscriptions")
@CrossOrigin(origins = "*")
public class PoleAllInscriptionController {
    @Autowired
    private PoleAllInscriptionService inscriptionService;

    @Operation(summary = "Get all inscriptions", description = "Retrieves a paginated list of all pole inscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved inscriptions", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-all-inscriptions/")
    public ResponseEntity<Map<String, Object>> getAllInscriptions(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<InscriptionModel> page = inscriptionService.getAllInscriptions(limit, offset);
        List<PoleAllInscriptionDto> inscriptionDtos = PoleAllInscriptionDtoMapper.toDtoList(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", inscriptionDtos);

        return ResponseEntity.ok(response);
    }
}
