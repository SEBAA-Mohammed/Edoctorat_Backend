package com.estf.edoctorat.controllers;

import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.mappers.CommissionDtoMapper;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.models.CommissionProfesseurModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.services.CommissionProfesseurService;
import com.estf.edoctorat.services.CommissionService;
import com.estf.edoctorat.services.LaboratoireService;
import com.estf.edoctorat.services.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commision/")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private LaboratoireService laboratoireService;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private CommissionProfesseurService commissionProfesseurService;


    @PostMapping
    @ResponseBody
    public ResponseEntity<CommissionModel> addCommission(@RequestBody CommissionCreationDto commissionDto){

        CommissionModel commission = CommissionDtoMapper.toCommission(commissionDto, laboratoireService );

        CommissionModel savedComm = commissionService.create(commission);

        commissionDto.getProfesseurs().stream()
                .map(profID -> professeurService.getById(profID).get())
                .forEach(prof -> commissionProfesseurService.create(new CommissionProfesseurModel(prof, savedComm)));

        return ResponseEntity.ok(savedComm);

    }

}
