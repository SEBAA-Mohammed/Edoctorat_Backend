package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.dto.CommissionDto;
import com.estf.edoctorat.mappers.CommissionDtoMapper;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.models.CommissionProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.*;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "Commissions", description = "APIs for managing commissions and their assignments")
@CrossOrigin(origins = "*")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private LaboratoireService laboratoireService;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private CommissionProfesseurService commissionProfesseurService;

    @Autowired
    private SujetService sujetService;

    @Operation(summary = "Create new commission", description = "Creates a new commission with assigned professors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commission created successfully", content = @Content(schema = @Schema(implementation = CommissionModel.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/commission/")
    @ResponseBody
    public ResponseEntity<CommissionModel> addCommission(
            @Parameter(description = "Commission creation details", required = true) @RequestBody CommissionCreationDto commissionDto) {

        CommissionModel commission = CommissionDtoMapper.toCommission(commissionDto, laboratoireService);

        CommissionModel savedComm = commissionService.create(commission);

        commissionDto.getProfesseurs().stream()
                .map(profID -> professeurService.getById(profID).get())
                .forEach(prof -> commissionProfesseurService.create(new CommissionProfesseurModel(prof, savedComm)));

        return ResponseEntity.ok(savedComm);

    }

    @Operation(summary = "Get laboratory commissions", description = "Retrieves paginated list of commissions for the current user's laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved commissions", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/commission/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getComissions(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam("0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        Page<CommissionModel> commissionsPages = commissionService.getByLabID(idLab, limit, offset);

        List<CommissionDto> listCommDto = commissionsPages.getContent().stream()
                .map(commission -> CommissionDtoMapper.toDto(commission, sujetService))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", commissionsPages.getTotalPages());
        response.put("next", commissionsPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listCommDto);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Delete commission", description = "Deletes a commission by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commission deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Commission not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/commission/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCommission(
            @Parameter(description = "ID of the commission to delete") @PathVariable long id) {

        try {

            commissionService.delete(id);
            return ResponseEntity.ok("Commission supprimer avec succés");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }

    @Operation(summary = "Get CED commissions", description = "Retrieves paginated list of commissions for the current user's CED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved CED commissions", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-ced-commissions/")
    public ResponseEntity<Map<String, Object>> getCommissionCed(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        Page<CommissionModel> listCommissions = commissionService.getCommissionByCed(currentUser, limit, offset);
        List<CommissionDto> listCommDto = listCommissions.stream()
                .map(commission -> CommissionDtoMapper.toDto(commission, sujetService))
                .toList();
        Map<String, Object> response = new HashMap<>();
        response.put("count", listCommDto.size());
        response.put("next", listCommissions.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listCommDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all commissions", description = "Retrieves paginated list of all commissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all commissions", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-all-commissions/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllCommissions(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        Page<CommissionModel> commissionsPage = commissionService.getAll(limit, offset);

        List<CommissionDto> listCommissionsDto = commissionsPage.getContent().stream()
                .map(commission -> CommissionDtoMapper.toDto(commission, sujetService))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", commissionsPage.getTotalPages());
        response.put("next", commissionsPage.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listCommissionsDto);

        return ResponseEntity.ok(response);

    }

}
