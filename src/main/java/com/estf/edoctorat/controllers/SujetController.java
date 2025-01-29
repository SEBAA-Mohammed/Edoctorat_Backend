package com.estf.edoctorat.controllers;


import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.*;
import com.estf.edoctorat.services.ProfesseurService;
import com.estf.edoctorat.services.SujetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<SujetDto> getSujetsLabo(HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        List<ProfesseurModel> listProfLabo = professeurService.getProfesseursByLabID(idLab);

        List<SujetDto> listSujetLabo = listProfLabo.stream()
                .flatMap( prof -> sujetService.getSujetsByProfID(prof.getId()).
                        stream().map(SujetDtoMapper::toDto))
                .toList();

        return listSujetLabo;

    }

    @GetMapping("/get-ced-sujets/")
    public List<SujetDto> getSujetsCed(HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        List<SujetModel> listSujetCed = sujetService.getSujetByCed(currentUser);
        return listSujetCed.stream()
                .map(SujetDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/sujetslabo")
    public SujetModel create(@RequestBody SujetModel sujetModel, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        return sujetService.create(sujetModel, currentUser);
    }

    @PutMapping("/{id}")
    public SujetModel update(@PathVariable long id, @RequestBody SujetModel sujetModel){
        return sujetService.update(id, sujetModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        sujetService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
