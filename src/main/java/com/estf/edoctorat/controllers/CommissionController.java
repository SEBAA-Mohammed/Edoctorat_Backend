package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.dto.CommissionDto;
import com.estf.edoctorat.mappers.CommissionDtoMapper;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.models.CommissionProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private LaboratoireService laboratoireService;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private CommissionProfesseurService commissionProfesseurService;

    @Autowired
    private SujetService sujetService;


    @PostMapping("/commission/")
    @ResponseBody
    public ResponseEntity<CommissionModel> addCommission(@RequestBody CommissionCreationDto commissionDto){

        CommissionModel commission = CommissionDtoMapper.toCommission(commissionDto, laboratoireService );

        CommissionModel savedComm = commissionService.create(commission);

        commissionDto.getProfesseurs().stream()
                .map(profID -> professeurService.getById(profID).get())
                .forEach(prof -> commissionProfesseurService.create(new CommissionProfesseurModel(prof, savedComm)));

        return ResponseEntity.ok(savedComm);

    }

    @GetMapping("/commission/")
    @ResponseBody
    public List<CommissionDto> getComissions(HttpServletRequest request){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        List<CommissionModel> listCommissions = commissionService.getByLabID(idLab);

        List<CommissionDto> listCommDto = listCommissions.stream()
                .map( commission -> CommissionDtoMapper.toDto(commission, sujetService) )
                .toList();

        return listCommDto;

    }

    @DeleteMapping("/commission/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCommission(@PathVariable long id){

        try{

            commissionService.delete(id);
            return ResponseEntity.ok("Commission supprimer avec succés");

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

    }

}
