package com.estf.edoctorat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estf.edoctorat.models.ConfigModel;
import com.estf.edoctorat.services.ConfigService;

@RestController
@RequestMapping("/api")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/get-base-config/")
    public ResponseEntity<ConfigModel> getBaseConfig() {
        return ResponseEntity.ok(configService.getBaseConfig());
    }
}