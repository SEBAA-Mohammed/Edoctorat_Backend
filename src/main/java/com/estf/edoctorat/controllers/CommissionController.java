package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.services.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commision/")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

//    @PostMapping
//    @ResponseBody
//    public ResponseEntity<CommissionModel> addCommission(@RequestBody CommissionCreationDto){
//
//    }

}
