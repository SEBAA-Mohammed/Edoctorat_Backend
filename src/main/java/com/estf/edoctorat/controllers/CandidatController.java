package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/candidats")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;
    @GetMapping()
    public List<CandidatModel> index() {
        return candidatService.getCandidats();
    }
}
