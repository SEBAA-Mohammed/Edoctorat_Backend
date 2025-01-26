package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.mappers.ProfesseurDtoMapper;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.services.ProfesseurService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/get-professeurs/")
public class ProfesseurController {

    private ProfesseurService professeurService;

    @GetMapping
    @ResponseBody
    public List<ProfesseurDto> getAllProfesseur(){

        List<ProfesseurModel> listProf = professeurService.getAll();

        return listProf.stream()
                .map(ProfesseurDtoMapper::toDto)
                .collect(Collectors.toList());

    }


}
