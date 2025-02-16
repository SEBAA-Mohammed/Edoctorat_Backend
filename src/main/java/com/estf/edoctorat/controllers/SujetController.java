package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.*;
import com.estf.edoctorat.services.ProfesseurService;
import com.estf.edoctorat.services.SujetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Subjects", description = "APIs for managing thesis subjects")
@CrossOrigin(origins = "*")
public class SujetController {

    @Autowired
    private SujetService sujetService;

    @Autowired
    private ProfesseurService professeurService;

    @Operation(summary = "Get laboratory subjects", description = "Retrieves a paginated list of subjects for the current user's laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/sujetslabo/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSujetsLabo(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        List<ProfesseurModel> listProfLabo = professeurService.getProfesseursByLabID(idLab);
        List<SujetDto> listSujetLabo = listProfLabo.stream()
                .flatMap(prof -> sujetService.getSujetsByProfID(prof.getId(), limit, offset).getContent().stream()
                        .map(SujetDtoMapper::toDto))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", listSujetLabo.size());
        response.put("next", listSujetLabo.size() == limit ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listSujetLabo);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get CED subjects", description = "Retrieves a paginated list of subjects for the CED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/get-ced-sujets/")
    public ResponseEntity<Map<String, Object>> getSujetsCed(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        Page<SujetModel> listSujetCed = sujetService.getSujetByCed(currentUser, limit, offset);

        List<SujetDto> listSujetced = listSujetCed.stream()
                .map(SujetDtoMapper::toDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("count", listSujetced.size());
        response.put("next", listSujetCed.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listSujetced);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create new subject", description = "Creates a new thesis subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject created successfully", content = @Content(schema = @Schema(implementation = SujetModel.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/sujetslabo/")
    public SujetModel create(
            @Parameter(description = "Subject details", required = true) @RequestBody SujetModel sujetModel,
            HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        return sujetService.create(sujetModel, currentUser);
    }

    @Operation(summary = "Update subject", description = "Updates an existing thesis subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject updated successfully", content = @Content(schema = @Schema(implementation = SujetModel.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    @PutMapping("/sujetslabo/{id}/")
    public SujetModel update(
            @Parameter(description = "ID of the subject to update") @PathVariable long id,
            @Parameter(description = "Updated subject details", required = true) @RequestBody SujetModel sujetModel) {

        return sujetService.update(id, sujetModel);
    }

    @Operation(summary = "Delete subject", description = "Deletes a thesis subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subject deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    @DeleteMapping("/sujetslabo/{id}/")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the subject to delete") @PathVariable long id) {

        sujetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publish subjects", description = "Publishes all approved subjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subjects published successfully"),
            @ApiResponse(responseCode = "500", description = "Publication failed")
    })
    @PatchMapping("/publier-sujets/")
    @ResponseBody
    public ResponseEntity<String> publierSujets() {

        try {
            sujetService.publierSujets();
            return ResponseEntity.ok("Sujets publiés avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la publication des sujets.");
        }
    }
}
