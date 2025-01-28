package com.estf.edoctorat.controllers;


import com.estf.edoctorat.dto.CalendrierDto;
import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import com.estf.edoctorat.services.DirecteurPoleCalendrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class CalendrierController {

    @Autowired
    private DirecteurPoleCalendrierService directeurPoleCalendrierService;

    @GetMapping("/get-calendrier/")
    public List<DirecteurPoleCalendrierModel> index() {

        return directeurPoleCalendrierService.getAll();
    }

    @PatchMapping("/update-calendrier/{calendrierId}")
    public ResponseEntity<DirecteurPoleCalendrierModel> updateCalendrier(
            @PathVariable int calendrierId,
            @RequestBody CalendrierDto updateRequest) {
        Optional<DirecteurPoleCalendrierModel> updatedCalendrier = directeurPoleCalendrierService.updateCalendrier(calendrierId, updateRequest.getDateDebut(), updateRequest.getDateFin());

        return updatedCalendrier
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
