package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.services.ScolariteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScolariteController {

    private final ScolariteService scolariteService;

    @GetMapping("/scolarite")
    public ResponseEntity<Map<String, Object>> getScolariteCandidats(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Page<PostulerDto> page = scolariteService.getScolariteCandidats(offset / limit, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", page.getContent());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/scolarite/{id}")
    public ResponseEntity<PostulerDto> updateScolarite(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return scolariteService.updateScolarite(id, updates);
    }
}