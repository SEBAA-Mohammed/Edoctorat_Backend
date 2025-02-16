package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.services.CandidatPostulerService;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Applications", description = "APIs for managing candidate applications")
@CrossOrigin(origins = "*")
public class PostulerController {

    @Autowired
    private CandidatPostulerService postulerService;

    @Operation(summary = "Get all candidate applications", description = "Retrieves a paginated list of all candidate applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved applications", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-all-candidats/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllCandidatPostuler(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<CandidatPostulerModel> postulerPages = postulerService.getAll(limit, offset);

        List<PostulerDto> listPostuler = postulerPages.getContent().stream()
                .map(PostulerDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", postulerPages.getTotalPages());
        response.put("next", postulerPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listPostuler);

        return ResponseEntity.ok(response);

    }

}
