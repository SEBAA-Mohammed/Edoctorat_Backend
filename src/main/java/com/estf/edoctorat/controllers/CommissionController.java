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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getComissions(HttpServletRequest request, @RequestParam(defaultValue = "50") int limit, @RequestParam("0") int offset){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        Page<CommissionModel> commissionsPages = commissionService.getByLabID(idLab, limit, offset);


        List<CommissionDto> listCommDto = commissionsPages.getContent().stream()
                .map( commission -> CommissionDtoMapper.toDto(commission, sujetService) )
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", commissionsPages.getTotalPages());
        response.put("next", commissionsPages.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listCommDto);


        return ResponseEntity.ok(response);

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

    @GetMapping("/get-ced-commissions/")
    public List<CommissionModel> getCommissionCed(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();
        List<CommissionModel> listCommissions =  commissionService.getCommissionByCed(currentUser);
        List<CommissionDto> listCommDto = listCommissions.stream()
                .map( commission -> CommissionDtoMapper.toDto(commission, sujetService) )
                .toList();
        return commissionService.getCommissionByCed(currentUser);
    }




    @GetMapping("/get-all-commissions/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllCommissions(@RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {

        Page<CommissionModel> commissionsPage = commissionService.getAll(limit, offset);

        List<CommissionDto> listCommissionsDto = commissionsPage.getContent().stream()
                .map(commission -> CommissionDtoMapper.toDto(commission, sujetService))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", commissionsPage.getTotalPages());
        response.put("next", commissionsPage.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listCommissionsDto);

        return ResponseEntity.ok(response);

    }


}
