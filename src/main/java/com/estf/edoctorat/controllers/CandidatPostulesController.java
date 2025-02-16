package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.CandidatPostulerService;
import com.estf.edoctorat.services.SujetService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "Candidate Applications", description = "APIs for managing candidate applications to subjects")
@CrossOrigin(origins = "*")
public class CandidatPostulesController {

        @Autowired
        private CandidatPostulerService candidatPostulerService;

        @Autowired
        private SujetService sujetService;

        @Operation(summary = "Get selected subjects", description = "Retrieves a paginated list of subjects selected by the authenticated candidate")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved selected subjects", content = @Content(schema = @Schema(implementation = Map.class))),
                        @ApiResponse(responseCode = "401", description = "Not authenticated"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/candidat-postules/")
        public ResponseEntity<Map<String, Object>> getSelectedSubjects(
                        @Parameter(description = "HTTP request object") HttpServletRequest request,
                        @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
                        @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

                UserDetails userDetails = (UserDetails) request.getAttribute("user");
                UserModel user = ((CustomUserDetails) userDetails).getUser();

                Page<CandidatPostulerModel> postules = candidatPostulerService.getByCandidatId(
                                user.getCandidat().getId(),
                                PageRequest.of(offset / limit, limit));

                Map<String, Object> response = new HashMap<>();
                response.put("count", postules.getTotalElements());
                response.put("next", postules.hasNext() ? offset + limit : null);
                response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
                response.put("results", postules.getContent().stream()
                                .map(PostulerDtoMapper::toDto)
                                .collect(Collectors.toList()));

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Apply to subject", description = "Creates a new application for the authenticated candidate to a specific subject")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Application created successfully", content = @Content(schema = @Schema(implementation = PostulerDto.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid subject ID"),
                        @ApiResponse(responseCode = "401", description = "Not authenticated"),
                        @ApiResponse(responseCode = "404", description = "Subject not found")
        })
        @PostMapping("/candidat-postules/")
        public ResponseEntity<PostulerDto> postuler(
                        @Parameter(description = "Subject ID", required = true) @RequestBody Map<String, Long> request,
                        HttpServletRequest httpRequest) {

                UserDetails userDetails = (UserDetails) httpRequest.getAttribute("user");
                UserModel user = ((CustomUserDetails) userDetails).getUser();

                SujetModel sujet = sujetService.getById(request.get("sujet"))
                                .orElseThrow(() -> new RuntimeException("Sujet not found"));

                CandidatPostulerModel postulation = new CandidatPostulerModel();
                postulation.setSujet(sujet);
                postulation.setCandidat(user.getCandidat());

                CandidatPostulerModel saved = candidatPostulerService.create(postulation);
                return ResponseEntity.ok(PostulerDtoMapper.toDto(saved));
        }

        @Operation(summary = "Delete application", description = "Deletes a candidate's application")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Application deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Application not found"),
                        @ApiResponse(responseCode = "401", description = "Not authenticated")
        })
        @DeleteMapping("/candidat-postules/{id}")
        public ResponseEntity<Void> deletePostule(
                        @Parameter(description = "ID of the application to delete") @PathVariable Long id) {
                candidatPostulerService.delete(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Update application", description = "Updates the thesis path file of an application")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Application updated successfully", content = @Content(schema = @Schema(implementation = PostulerDto.class))),
                        @ApiResponse(responseCode = "404", description = "Application not found"),
                        @ApiResponse(responseCode = "401", description = "Not authenticated")
        })
        @PatchMapping("/candidat-postules/{id}")
        public ResponseEntity<PostulerDto> updatePostuler(
                        @Parameter(description = "ID of the application to update") @PathVariable Long id,
                        @Parameter(description = "New thesis file path", required = true) @RequestBody Map<String, String> pathThese) {

                CandidatPostulerModel postulation = candidatPostulerService.getById(id)
                                .orElseThrow(() -> new RuntimeException("Postulation not found"));

                postulation.setPathFile(pathThese.get("pathFile"));

                CandidatPostulerModel updated = candidatPostulerService.update(id, postulation);
                return ResponseEntity.ok(PostulerDtoMapper.toDto(updated));
        }

        @Operation(summary = "Get published subjects", description = "Retrieves a paginated list of all published subjects")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects", content = @Content(schema = @Schema(implementation = Map.class))),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("/get-published-subjects/")
        @PreAuthorize("permitAll()")
        public ResponseEntity<Map<String, Object>> getPublishedSubjects(
                        @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
                        @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {
                try {
                        Page<SujetModel> sujets = sujetService.getPublishedSubjects(limit, offset);
                        System.err.println("sujets: " + sujets);
                        Map<String, Object> response = new HashMap<>();
                        response.put("count", sujets.getTotalElements());
                        response.put("next", sujets.hasNext() ? offset + limit : null);
                        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
                        response.put("results", sujets.getContent().stream()
                                        .map(SujetDtoMapper::toDto)
                                        .collect(Collectors.toList()));

                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(Map.of("error", "Error retrieving published subjects"));
                }
        }

}