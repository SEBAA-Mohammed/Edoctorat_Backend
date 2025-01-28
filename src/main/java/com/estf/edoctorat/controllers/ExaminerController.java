package com.estf.edoctorat.controllers;

import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.services.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/examiner/")

public class ExaminerController {

    @Autowired
    private ExaminerService examinerService;

    @GetMapping
    public List<ExaminerModel> index(){
        return examinerService.getAll();
    }
}
