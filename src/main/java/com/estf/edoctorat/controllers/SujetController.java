package com.estf.edoctorat.controllers;


import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ProfesseurService;
import com.estf.edoctorat.services.SujetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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


}
