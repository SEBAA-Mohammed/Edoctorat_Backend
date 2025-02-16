package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.mappers.PostulerDtoMapper2;
import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ProfesseurCandidatsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProfesseurCandidatsController {
    @Autowired
    private ProfesseurCandidatsService professeurCandidatsService;

    @GetMapping("/get-professeur-candidats/")
    public ResponseEntity<Map<String, Object>> getProfesseurCandidats(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        List<CandidatPostulerModel> candidats = professeurCandidatsService.getProfesseurCandidats(user);
        List<PostulerDto> candidatDtos = PostulerDtoMapper2.toDtoList(candidats);

        Map<String, Object> response = new HashMap<>();
        response.put("results", candidatDtos);
        response.put("count", candidatDtos.size());
        response.put("next", null);
        response.put("previous", null);

        return ResponseEntity.ok(response);
    }
}