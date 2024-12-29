package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.repositories.ProfesseurRepository;

public class ProfesseurService {
    @Autowired
    private ProfesseurRepository professeurRepository;

    public List<ProfesseurModel> getAll() {
        return professeurRepository.findAll();
    }

    public Optional<ProfesseurModel> getById(long id) {
        return professeurRepository.findById(id);
    }

    public ProfesseurModel create(ProfesseurModel ProfesseurModel) {
        return professeurRepository.save(ProfesseurModel);
    }

    public ProfesseurModel update(long id, ProfesseurModel ProfesseurModel) {
        if (professeurRepository.existsById(id)) {
            ProfesseurModel.setId(id);
            return professeurRepository.save(ProfesseurModel);
        }
        return null;
    }

    public void delete(long id) {
        if (professeurRepository.existsById(id)) {
            professeurRepository.deleteById(id);
        } else {
            throw new RuntimeException("professeur not found");
        }
    }
}
