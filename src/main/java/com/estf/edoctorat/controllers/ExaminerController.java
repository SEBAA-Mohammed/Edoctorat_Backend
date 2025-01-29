package com.estf.edoctorat.controllers;

import com.estf.edoctorat.config.CustomUserDetails;
import com.estf.edoctorat.dto.ExaminerDto;
import com.estf.edoctorat.dto.ItemValider;
import com.estf.edoctorat.dto.PreselectionParSujetDto;
import com.estf.edoctorat.mappers.ExaminerDtoMapper;
import com.estf.edoctorat.mappers.PreselectionParSujetDtoMapper;
import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.ExaminerService;
import com.estf.edoctorat.services.ProfesseurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class ExaminerController {

    @Autowired
    private ExaminerService examinerService;

    @Autowired
    private ProfesseurService professeurService;

    @GetMapping("examiner/")
    public List<ExaminerModel> index(){
        return examinerService.getAll();
    }

    @GetMapping("/labo_candidat/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCandidats(HttpServletRequest request, @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        Page<ExaminerModel> examinerLabo = examinerService.getByLabID(idLab, limit, offset);


        List<ExaminerDto> listExaminer = examinerLabo.getContent().stream()
                .map(ExaminerDtoMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("count", examinerLabo.getTotalElements());
        response.put("next", examinerLabo.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", listExaminer);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/get-sujet-candidat/{id}/")
    @ResponseBody
    public List<PreselectionParSujetDto> getCandidatsParSujet(@PathVariable long sujetId){

        List<ExaminerModel> listExaminerSujet = examinerService.getBySujetID(sujetId);

        return listExaminerSujet.stream()
                .map(PreselectionParSujetDtoMapper::toDto)
                .toList();

    }

    @PutMapping("/labo_valider_examiner/{id}")
    @ResponseBody
    public ExaminerModel validerCandidat(@PathVariable long id, @RequestBody ItemValider item){

        ExaminerModel examiner =  examinerService.getById(id).get();

        examiner.setValider(item.getValider());

        return examinerService.update(id, examiner);

    }

    @GetMapping("/get-ced-resultats/")
    public ResponseEntity<Map<String, Object>> getExaminerCed(HttpServletRequest request ,  @RequestParam(defaultValue = "50") int limit, @RequestParam(defaultValue = "0") int offset) {
        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel currentUser = ((CustomUserDetails) userDetails).getUser();

        Page<ExaminerModel> resultats = examinerService.getByCedID(currentUser, limit, offset);

        Map<String, Object> response = new HashMap<>();
        response.put("count", resultats.getTotalElements());
        response.put("next", resultats.hasNext() ? offset + limit : null);
        response.put("previous", offset > 0 ? Math.max(0, offset - limit) : null);
        response.put("results", resultats);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/publier-liste-attente/")
    @ResponseBody
    public ResponseEntity<?> publierListeAttente(){

        examinerService.publierListeAttente();
        return ResponseEntity.ok("List d'attente publier avec succés!");

    }

    @PostMapping("/publier-liste-principale/")
    @ResponseBody
    public ResponseEntity<?> publierListePrincipale(){

        examinerService.publierListePrincipale();
        return ResponseEntity.ok("List principale publier avec succés!");

    }

}
