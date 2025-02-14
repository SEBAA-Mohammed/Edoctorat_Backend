package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.*;
import com.estf.edoctorat.services.OperationsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OperationsController {

    private final OperationsService operationsService;

    @GetMapping("/formation-doctorale")
    public ResponseEntity<List<FormationdoctoraleDto>> getFormationDoctorales() {
        return ResponseEntity.ok(operationsService.getAllFormationDoctorales());
    }


    @GetMapping("/sujets/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSujets() {
        List<SujetDto> sujets = operationsService.getAllSujets();

        Map<String, Object> response = new HashMap<>();
        response.put("count", sujets.size());
        response.put("results", sujets);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/sujets/{id}")
    public ResponseEntity<Sujet2Dto> getSujet(@PathVariable Long id) {
        return ResponseEntity.ok(operationsService.getSujetById(id));
    }


    @PostMapping("/sujets/")
    public ResponseEntity<Sujet2Dto> createSujet(@RequestBody CreateSujetDto sujet) {
        return ResponseEntity.ok(operationsService.createSujet(sujet));
    }

    @PutMapping("/sujets/{id}")
    public ResponseEntity<Sujet2Dto> updateSujet(@PathVariable Long id, @RequestBody Sujet2Dto sujet) {
        return ResponseEntity.ok(operationsService.updateSujet(id, sujet));
    }

    @DeleteMapping("/sujets/{id}")
    public ResponseEntity<Void> deleteSujet(@PathVariable Long id) {
        operationsService.deleteSujet(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/participant")
    public ResponseEntity<List<CommissionDto>> getCommissions() {
        return ResponseEntity.ok(operationsService.getAllCommissions());
    }

    @GetMapping("/examiner")
    public ResponseEntity<Map<String, Object>> getResultats(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Page<ExaminerDto> page = operationsService.getResultats(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inscrits")
    public ResponseEntity<Map<String, Object>> getMesInscrits(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Page<InscriptionDto> page = operationsService.getMesInscrits(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }
    //    @GetMapping("/get-professeurs")
//    public ResponseEntity<List<ProfesseurDto>> getProfesseurs() {
//        return ResponseEntity.ok(operationsService.getAllProfesseurs());
//    }
}