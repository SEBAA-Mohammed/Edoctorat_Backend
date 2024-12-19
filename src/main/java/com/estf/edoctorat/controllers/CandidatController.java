package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidats")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @GetMapping()
    public List<CandidatModel> index() {
        return candidatService.getCandidats();
    }

    @GetMapping("/candidatById/{id}")
    public Optional<CandidatModel> getById(@PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @GetMapping("/candidatByName/{name}")
    public List<CandidatModel> getByName(@PathVariable String name) {
        return candidatService.getCandidatByName(name);
    }
}
