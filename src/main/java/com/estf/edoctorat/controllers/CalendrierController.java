package com.estf.edoctorat.controllers;


import com.estf.edoctorat.dto.CalendrierDto;
import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.services.DirecteurPoleCalendrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class CalendrierController {

    @Autowired
    private DirecteurPoleCalendrierService directeurPoleCalendrierService;

    @GetMapping("/get-calendrier/")
    public ResponseEntity<Map<String, Object>> index( @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {

        Page<DirecteurPoleCalendrierModel> resultats = directeurPoleCalendrierService.getAll(limit, offset);
        Map<String, Object> response = new HashMap<>();
        response.put("count", resultats.getTotalElements());
        response.put("next", resultats.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", resultats);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-calendrier/{calendrierId}/")
    public ResponseEntity<DirecteurPoleCalendrierModel> updateCalendrier(
            @PathVariable int calendrierId,
            @RequestBody CalendrierDto updateRequest) {
        Optional<DirecteurPoleCalendrierModel> updatedCalendrier = directeurPoleCalendrierService.updateCalendrier(calendrierId, updateRequest.getDateDebut(), updateRequest.getDateFin());

        return updatedCalendrier
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
