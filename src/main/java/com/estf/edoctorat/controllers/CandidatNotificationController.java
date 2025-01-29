package com.estf.edoctorat.controllers;


import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.CandidatNotificationModel;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.services.CandidatNotificationService;
import com.estf.edoctorat.services.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CandidatNotificationController {

    @Autowired
    private ExaminerService examinerService;
    @Autowired
    private CandidatNotificationService candidatNotificationService;

    @PostMapping("/convoque-candidat/{id}/")
    @ResponseBody
    public ResponseEntity<?> envoyerNotification(@PathVariable long id) {

        ExaminerModel examiner = examinerService.getById(id).get();

        String notifType = "COMMISSION";

        CandidatNotificationModel notif = new CandidatNotificationModel( notifType, examiner.getCandidat(), examiner.getCommission(), examiner.getSujet() );

        CandidatNotificationModel newNotif = candidatNotificationService.create(notif);

        return ResponseEntity.ok(newNotif);
    }

}
