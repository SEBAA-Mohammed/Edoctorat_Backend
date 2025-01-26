package com.estf.edoctorat.controllers;


import com.estf.edoctorat.dto.FormationdoctoraleDto;
import com.estf.edoctorat.mappers.FormationdoctoraleDtoMapper;
import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.services.FormationdoctoraleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formation-doctorale/")
public class FormationdoctoraleController {

    @Autowired
    private FormationdoctoraleService formationdoctoraleService;

    @GetMapping
    @ResponseBody
    public List<FormationdoctoraleDto> getAllFormationdoctorale(){

        List<FormationdoctoraleModel> listFormationD = formationdoctoraleService.getAll();

        return listFormationD.stream()
                .map(FormationdoctoraleDtoMapper::toDto)
                .collect(Collectors.toList());

    }

}
