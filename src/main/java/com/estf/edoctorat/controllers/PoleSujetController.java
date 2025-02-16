package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.Sujet2Dto;
import com.estf.edoctorat.mappers.SujetMapper;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.services.PoleSujetService;
import com.estf.edoctorat.services.SujetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PoleSujetController {
    @Autowired
    private PoleSujetService sujetService;

    @GetMapping("/get-all-sujets/")
    public ResponseEntity<Map<String, Object>> getAllSujets(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Page<SujetModel> page = sujetService.getAllSujets(limit, offset);
        List<Sujet2Dto> sujetDtos = SujetMapper.toDtoList(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", sujetDtos);

        return ResponseEntity.ok(response);
    }
}