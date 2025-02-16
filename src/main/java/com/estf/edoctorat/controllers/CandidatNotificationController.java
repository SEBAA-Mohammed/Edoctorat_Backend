package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.NotificationDto;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.CandidatNotificationModel;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.CandidatNotificationService;
import com.estf.edoctorat.services.CandidatPostulerService;
import com.estf.edoctorat.services.ExaminerService;
import com.estf.edoctorat.services.SujetService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "Candidate Notifications", description = "APIs for managing candidate notifications and inscriptions")
@CrossOrigin(origins = "*")
public class CandidatNotificationController {

    @Autowired
    private ExaminerService examinerService;
    @Autowired
    private CandidatNotificationService candidatNotificationService;
    @Autowired
    private SujetService sujetService;

    @Autowired
    private CandidatPostulerService candidatPostulerService;

    @Operation(summary = "Send notification to candidate", description = "Sends a commission notification to a candidate based on examiner ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification sent successfully", content = @Content(schema = @Schema(implementation = CandidatNotificationModel.class))),
            @ApiResponse(responseCode = "404", description = "Examiner not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/convoque-candidat/{id}/")
    public ResponseEntity<?> envoyerNotification(
            @Parameter(description = "ID of the examiner", required = true) @PathVariable long id) {

        ExaminerModel examiner = examinerService.getById(id).get();

        String notifType = "COMMISSION";

        CandidatNotificationModel notif = new CandidatNotificationModel(notifType, examiner.getCandidat(),
                examiner.getCommission(), examiner.getSujet());

        CandidatNotificationModel newNotif = candidatNotificationService.create(notif);

        return ResponseEntity.ok(newNotif);
    }

    @Operation(summary = "Get candidate notifications", description = "Retrieves paginated list of notifications for the authenticated candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/get-candidat-notifications/")
    public ResponseEntity<Map<String, Object>> getCandidatNotifications(
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Page offset") @RequestParam(defaultValue = "0") int offset,
            HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        Page<NotificationDto> notifications = candidatNotificationService
                .getCandidatNotifications(user.getCandidat().getId(), limit, offset);

        Map<String, Object> response = new HashMap<>();
        response.put("count", notifications.getTotalElements());
        response.put("next", notifications.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", notifications.getContent());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add inscription to subject", description = "Creates a new inscription for the authenticated candidate to a specific subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription added successfully", content = @Content(schema = @Schema(implementation = SujetDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or subject not found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/add-inscription/{sujetId}/")
    public ResponseEntity<SujetDto> addInscription(
            @Parameter(description = "ID of the subject to inscribe to", required = true) @PathVariable Long sujetId,
            HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        try {
            SujetModel sujet = sujetService.getById(sujetId)
                    .orElseThrow(() -> new RuntimeException("Sujet not found"));

            CandidatPostulerModel inscription = new CandidatPostulerModel();
            inscription.setCandidat(user.getCandidat());
            inscription.setSujet(sujet);

            CandidatPostulerModel savedInscription = candidatPostulerService.create(inscription);

            return ResponseEntity.ok(SujetDtoMapper.toDto(savedInscription.getSujet()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
