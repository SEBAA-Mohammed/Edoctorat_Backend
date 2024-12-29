package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.ProfesseurLaboratoirModel;
import com.estf.edoctorat.repositories.ProfesseurLaboratoirRepository;

@Service
public class ProfesseurLaboratoirService {
    @Autowired
    private ProfesseurLaboratoirRepository professeurLaboratoirRepository;

    public List<ProfesseurLaboratoirModel> getAll() {
        return professeurLaboratoirRepository.findAll();
    }

    public Optional<ProfesseurLaboratoirModel> getById(long id) {
        return professeurLaboratoirRepository.findById(id);
    }

    public ProfesseurLaboratoirModel create(ProfesseurLaboratoirModel ProfesseurLaboratoirModel) {
        return professeurLaboratoirRepository.save(ProfesseurLaboratoirModel);
    }

    public ProfesseurLaboratoirModel update(long id, ProfesseurLaboratoirModel ProfesseurLaboratoirModel) {
        if (professeurLaboratoirRepository.existsById(id)) {
            ProfesseurLaboratoirModel.setId(id);
            return professeurLaboratoirRepository.save(ProfesseurLaboratoirModel);
        }
        return null;
    }

    public void delete(long id) {
        if (professeurLaboratoirRepository.existsById(id)) {
            professeurLaboratoirRepository.deleteById(id);
        } else {
            throw new RuntimeException("professeur not found");
        }
    }
}
