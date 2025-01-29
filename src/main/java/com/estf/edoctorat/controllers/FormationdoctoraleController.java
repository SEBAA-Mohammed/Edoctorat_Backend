package com.estf.edoctorat.controllers;


import com.estf.edoctorat.dto.FormationdoctoraleDto;
import com.estf.edoctorat.mappers.FormationdoctoraleDtoMapper;
import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.services.FormationdoctoraleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formation-doctorale/")
public class FormationdoctoraleController {

    @Autowired
    private FormationdoctoraleService formationdoctoraleService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllFormationdoctorale(@RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset){

        Page<FormationdoctoraleModel> listFormationD = formationdoctoraleService.getAll(limit, offset);

        listFormationD.stream()
                .map(FormationdoctoraleDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", listFormationD.getTotalElements());
        response.put("next", listFormationD.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listFormationD);
        return ResponseEntity.ok(response);

    }

}
