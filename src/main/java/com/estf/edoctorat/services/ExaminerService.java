package com.estf.edoctorat.services;

import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.repositories.ExaminerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminerService {
    @Autowired
    private ExaminerRepository examinerRepository;

    public List<ExaminerModel> getAll() {
        return examinerRepository.findAll();
    }

    public Optional<ExaminerModel> getById(long id) {
        return examinerRepository.findById(id);
    }

    public ExaminerModel create(ExaminerModel examinerModel) {
        return examinerRepository.save(examinerModel);
    }

    public ExaminerModel update(long id, ExaminerModel examinerModel) {
        if (examinerRepository.existsById(id)) {
            examinerModel.setId(id);
            return examinerRepository.save(examinerModel);
        }
        return null;
    }

    public void delete(long id) {
        if(examinerRepository.existsById(id)) {
            examinerRepository.deleteById(id);
        }else{
            throw new RuntimeException("Examination not found");
        }
    }

    public List<ExaminerModel> getByLabID(long id) { return examinerRepository.findByLabo(id); }

    public List<ExaminerModel> getBySujetID(long id) { return examinerRepository.findBySujetID(id); }

}
