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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ExaminerDto> getCandidats(HttpServletRequest request){

        UserDetails userDetails = (UserDetails) request.getAttribute("user");
        UserModel user = ((CustomUserDetails) userDetails).getUser();

        long idLab = professeurService.getByUser(user).get().getLabo_id();

        List<ExaminerModel> examinerLabo = examinerService.getByLabID(idLab);

        return examinerLabo.stream()
                .map(ExaminerDtoMapper::toDto)
                .toList();

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

}
