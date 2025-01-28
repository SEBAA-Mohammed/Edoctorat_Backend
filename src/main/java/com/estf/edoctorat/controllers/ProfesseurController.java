package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.mappers.ProfesseurDtoMapper;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ProfesseurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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


    @GetMapping("/labo_professeur")
    @ResponseBody
    public List<ProfesseurDto> getAllProfLab(HttpServletRequest request){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long labID = professeurService.getByUser(user).get().getLabo_id();

        return professeurService.getProfesseursByLabID(labID).stream()
                .map(ProfesseurDtoMapper::toDto)
                .toList();

    }

}
