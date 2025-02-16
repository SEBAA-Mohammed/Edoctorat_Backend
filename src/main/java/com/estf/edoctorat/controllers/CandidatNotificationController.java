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

@RestController
@RequestMapping("/api")
public class CandidatNotificationController {

    @Autowired
    private ExaminerService examinerService;
    @Autowired
    private CandidatNotificationService candidatNotificationService;
    @Autowired
    private SujetService sujetService;

    @Autowired
    private CandidatPostulerService candidatPostulerService;

    @PostMapping("/convoque-candidat/{id}/")
    @ResponseBody
    public ResponseEntity<?> envoyerNotification(@PathVariable long id) {

        ExaminerModel examiner = examinerService.getById(id).get();

        String notifType = "COMMISSION";

        CandidatNotificationModel notif = new CandidatNotificationModel(notifType, examiner.getCandidat(),
                examiner.getCommission(), examiner.getSujet());

        CandidatNotificationModel newNotif = candidatNotificationService.create(notif);

        return ResponseEntity.ok(newNotif);
    }

    @GetMapping("/get-candidat-notifications/")
    public ResponseEntity<Map<String, Object>> getCandidatNotifications(
            HttpServletRequest request,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {
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

    @PostMapping("/add-inscription/{sujetId}/")
    public ResponseEntity<SujetDto> addInscription(
            @PathVariable Long sujetId,
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
