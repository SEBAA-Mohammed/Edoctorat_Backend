package com.estf.edoctorat.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/candidats")
public class CandidatController {

    @GetMapping()
    public String candidats() {
        return "Candidats";
    }
}
