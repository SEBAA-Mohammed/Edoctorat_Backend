package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.dto.ProfesseurDto3;
import com.estf.edoctorat.mappers.ProfesseurDtoMapper;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ProfesseurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Professors", description = "APIs for managing professors")
@CrossOrigin(origins = "*")
public class ProfesseurController {

    private final ProfesseurService professeurService;

    public ProfesseurController(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @Operation(summary = "Get all professors", description = "Retrieves a paginated list of all professors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved professors", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-professeurs/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllProfesseur(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {
        Page<ProfesseurDto3> listProf = professeurService.getAll(limit, offset);

        Map<String, Object> response = new HashMap<>();
        response.put("count", listProf.getTotalElements());
        response.put("next", listProf.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listProf.getContent());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get laboratory professors", description = "Retrieves a paginated list of professors in the user's laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved laboratory professors", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/labo_professeur/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllProfLab(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long labID = professeurService.getByUser(user).get().getLabo_id();

        Page<ProfesseurModel> profLaboPages = professeurService.getProfesseursByLabID(labID, limit, offset);
        List<ProfesseurDto> listProfsLabo = profLaboPages.getContent().stream()
                .map(ProfesseurDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", profLaboPages.getTotalElements());
        response.put("next", profLaboPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listProfsLabo);

        return ResponseEntity.ok(response);

    }

}
