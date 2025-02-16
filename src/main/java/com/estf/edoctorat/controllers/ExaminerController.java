package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.ExaminerDto;
import com.estf.edoctorat.dto.ItemValider;
import com.estf.edoctorat.dto.PreselectionParSujetDto;
import com.estf.edoctorat.mappers.ExaminerDtoMapper;
import com.estf.edoctorat.mappers.PreselectionParSujetDtoMapper;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ExaminerService;
import com.estf.edoctorat.services.ProfesseurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Examiner", description = "APIs for managing examination and candidate evaluation")
@CrossOrigin(origins = "*")
public class ExaminerController {

    @Autowired
    private ExaminerService examinerService;

    @Autowired
    private ProfesseurService professeurService;

    @Operation(summary = "Get all examiners", description = "Retrieves a list of all examiners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved examiners", content = @Content(schema = @Schema(implementation = ExaminerModel.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/examiner/")
    public List<ExaminerModel> index() {
        return examinerService.getAll();
    }

    @Operation(summary = "Get laboratory candidates", description = "Retrieves paginated list of candidates for the current user's laboratory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidates", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/labo_candidat/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCandidats(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        Page<ExaminerModel> examinerLabo = examinerService.getByLabID(idLab, limit, offset);

        List<ExaminerDto> listExaminer = examinerLabo.getContent().stream()
                .map(ExaminerDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", examinerLabo.getTotalElements());
        response.put("next", examinerLabo.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listExaminer);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Get candidates by subject", description = "Retrieves list of candidates for a specific subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved candidates", content = @Content(schema = @Schema(implementation = PreselectionParSujetDto.class)))
    })
    @GetMapping("/get-sujet-candidat/{id}/")
    @ResponseBody
    public List<PreselectionParSujetDto> getCandidatsParSujet(
            @Parameter(description = "ID of the subject") @PathVariable long sujetId) {

        List<ExaminerModel> listExaminerSujet = examinerService.getBySujetID(sujetId);

        return listExaminerSujet.stream()
                .map(PreselectionParSujetDtoMapper::toDto)
                .toList();

    }

    @Operation(summary = "Validate candidate", description = "Updates validation status of a candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate validation updated", content = @Content(schema = @Schema(implementation = ExaminerModel.class))),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    @PutMapping("/labo_valider_examiner/{id}/")
    @ResponseBody
    public ExaminerModel validerCandidat(
            @Parameter(description = "ID of the candidate") @PathVariable long id,
            @Parameter(description = "Validation details") @RequestBody ItemValider item) {

        ExaminerModel examiner = examinerService.getById(id).get();

        examiner.setValider(item.getValider());

        return examinerService.update(id, examiner);

    }

    @Operation(summary = "Get CED results", description = "Retrieves paginated list of examination results for the CED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved results", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/get-ced-resultats/")
    public ResponseEntity<Map<String, Object>> getExaminerCed(
            HttpServletRequest request,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();

        Page<ExaminerModel> resultats = examinerService.getByCedID(currentUser, limit, offset);

        Map<String, Object> response = new HashMap<>();
        response.put("count", resultats.getTotalElements());
        response.put("next", resultats.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", resultats);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Publish waiting list", description = "Publishes the waiting list of candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waiting list published successfully"),
            @ApiResponse(responseCode = "500", description = "Publication failed")
    })
    @PostMapping("/publier-liste-attente/")
    @ResponseBody
    public ResponseEntity<?> publierListeAttente() {

        examinerService.publierListeAttente();
        return ResponseEntity.ok("List d'attente publier avec succés!");

    }

    @Operation(summary = "Publish main list", description = "Publishes the main list of accepted candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Main list published successfully"),
            @ApiResponse(responseCode = "500", description = "Publication failed")
    })
    @PostMapping("/publier-liste-principale/")
    @ResponseBody
    public ResponseEntity<?> publierListePrincipale() {

        examinerService.publierListePrincipale();
        return ResponseEntity.ok("List principale publier avec succés!");

    }

}
