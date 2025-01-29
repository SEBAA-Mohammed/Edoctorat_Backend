package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.mappers.ProfesseurDtoMapper;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ProfesseurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesseurController {

    private ProfesseurService professeurService;

    @GetMapping("/get-professeurs")
    @ResponseBody
    public List<ProfesseurDto> getAllProfesseur(){

        List<ProfesseurModel> listProf = professeurService.getAll();

        return listProf.stream()
                .map(ProfesseurDtoMapper::toDto)
                .collect(Collectors.toList());

    }


    @GetMapping("/labo_professeur/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllProfLab(HttpServletRequest request, @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset ){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long labID = professeurService.getByUser(user).get().getLabo_id();

        Page<ProfesseurModel> profLaboPages = professeurService.getProfesseursByLabID(labID, limit, offset);
        List<ProfesseurDto> listProfsLabo = profLaboPages.getContent().stream()
                .map(ProfesseurDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", profLaboPages.getTotalElements());
        response.put("next", profLaboPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listProfsLabo);

        return ResponseEntity.ok(response);

    }

}
