package com.estf.edoctorat.controllers;


import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.*;
import com.estf.edoctorat.services.ProfesseurService;
import com.estf.edoctorat.services.SujetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")

public class SujetController {

    @Autowired
    private SujetService sujetService;

    @Autowired
    private ProfesseurService professeurService;


    @GetMapping("/sujetslabo")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSujetsLabo(HttpServletRequest request, @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        List<ProfesseurModel> listProfLabo = professeurService.getProfesseursByLabID(idLab);
        List<SujetDto> listSujetLabo = listProfLabo.stream()
                .flatMap( prof -> sujetService.getSujetsByProfID(prof.getId(), limit, offset).getContent().
                        stream().map(SujetDtoMapper::toDto))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", listSujetLabo.size());
        response.put("next", listSujetLabo.size() == limit ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listSujetLabo);


        return ResponseEntity.ok(response);

    }

    @GetMapping("/get-ced-sujets/")
    public ResponseEntity<Map<String, Object>> getSujetsCed(HttpServletRequest request, @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        Page<SujetModel> listSujetCed = sujetService.getSujetByCed(currentUser, limit, offset);

        List<SujetDto> listSujetced =listSujetCed.stream()
                .map(SujetDtoMapper::toDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("count", listSujetced.size());
        response.put("next", listSujetCed.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listSujetced);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sujetslabo")
    public SujetModel create(@RequestBody SujetModel sujetModel, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        return sujetService.create(sujetModel, currentUser);
    }

    @PutMapping("/sujetslabo/{id}")
    public SujetModel update(@PathVariable long id, @RequestBody SujetModel sujetModel){
        return sujetService.update(id, sujetModel);
    }

    @DeleteMapping("/sujetslabo/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        sujetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/publier-sujets/")
    @ResponseBody
    public ResponseEntity<String> publierSujets(){

        try {
            sujetService.publierSujets();
            return ResponseEntity.ok("Sujets publiés avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la publication des sujets.");
        }

    }

}
