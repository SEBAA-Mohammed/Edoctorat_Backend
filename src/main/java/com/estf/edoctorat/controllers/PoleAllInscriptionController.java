package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PoleAllInscriptionDto;
import com.estf.edoctorat.mappers.PoleAllInscriptionDtoMapper;
import com.estf.edoctorat.models.InscriptionModel;
import com.estf.edoctorat.services.PoleAllInscriptionService;
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
public class PoleAllInscriptionController {
    @Autowired
    private PoleAllInscriptionService inscriptionService;

    @GetMapping("/get-all-inscriptions")
    public ResponseEntity<Map<String, Object>> getAllInscriptions(
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        Page<InscriptionModel> page = inscriptionService.getAllInscriptions(limit, offset);
        List<PoleAllInscriptionDto> inscriptionDtos = PoleAllInscriptionDtoMapper.toDtoList(page.getContent());

        Map<String, Object> response = new HashMap<>();
        response.put("count", page.getTotalElements());
        response.put("next", page.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", inscriptionDtos);

        return ResponseEntity.ok(response);
    }
}
