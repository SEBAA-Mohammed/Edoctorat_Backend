package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.services.CandidatPostulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostulerController {

    @Autowired
    private CandidatPostulerService postulerService;


    @GetMapping("get-all-candidats")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllCandidatPostuler(@RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {

        Page<CandidatPostulerModel> postulerPages = postulerService.getAll(limit, offset);

        List<PostulerDto> listPostuler = postulerPages.getContent().stream()
                .map(PostulerDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", postulerPages.getTotalPages());
        response.put("next", postulerPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listPostuler);

        return ResponseEntity.ok(response);

    }


}
