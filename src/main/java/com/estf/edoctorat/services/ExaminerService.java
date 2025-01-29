package com.estf.edoctorat.services;

import com.estf.edoctorat.models.ExaminerModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.ExaminerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<ExaminerModel> getByLabID(long id, int limit, int offset) { return examinerRepository.findByLabo(id, PageRequest.of(offset / limit, limit)); }

    public List<ExaminerModel> getBySujetID(long id) { return examinerRepository.findBySujetID(id); }

    public Page<ExaminerModel> getByCedID(UserModel currentUser, int limit, int offset ){
        long idCed = currentUser.getProfesseur().getCed().getId();
        return examinerRepository.findByCedId(idCed, PageRequest.of(offset / limit, limit));
    }

}
