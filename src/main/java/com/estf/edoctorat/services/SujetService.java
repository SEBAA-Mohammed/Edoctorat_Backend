package com.estf.edoctorat.services;

import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.repositories.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SujetService {
    @Autowired
    private SujetRepository sujetRepository;

    public List<SujetModel> getAll() {
        return sujetRepository.findAll();
    }

    public Optional<SujetModel> getById(long id) {
        return sujetRepository.findById(id);
    }

    public SujetModel create(SujetModel sujetModel) {
        return sujetRepository.save(sujetModel);
    }

    public SujetModel update(long id, SujetModel sujetModel) {
        if (sujetRepository.existsById(id)) {
            sujetModel.setId(id);
            return sujetRepository.save(sujetModel);
        }
        return null;
    }

    public void delete(long id) {
        if(sujetRepository.existsById(id)) {
            sujetRepository.deleteById(id);
        }else{
            throw new RuntimeException("sujet not found");
        }
    }
}
