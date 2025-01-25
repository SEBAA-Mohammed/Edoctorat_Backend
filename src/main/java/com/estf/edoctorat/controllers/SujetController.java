package com.estf.edoctorat.controllers;


import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.PermissionModel;
import com.estf.edoctorat.services.SujetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/sujetslabo/")

public class SujetController {

    @Autowired
    private SujetService sujetService;

    @GetMapping
    @ResponseBody
    public List<SujetDto> getSujetsLabo() {
        return sujetService.getAll()
                .stream()
                .map( SujetDtoMapper::toDto )
                .collect(toList());
    }


}
