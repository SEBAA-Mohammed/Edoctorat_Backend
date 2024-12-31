package com.estf.edoctorat.services;

import com.estf.edoctorat.models.InscriptionModel;
import com.estf.edoctorat.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public List<InscriptionModel> getAll() {
        return inscriptionRepository.findAll();
    }

    public Optional<InscriptionModel> getById(long id) {
        return inscriptionRepository.findById(id);
    }

    public InscriptionModel create(InscriptionModel inscriptionModel) {
        return inscriptionRepository.save(inscriptionModel);
    }

    public InscriptionModel update(long id, InscriptionModel inscriptionModel) {
        if (inscriptionRepository.existsById(id)) {
            inscriptionModel.setId(id);
            return inscriptionRepository.save(inscriptionModel);
        }
        return null;
    }

    public void delete(long id) {
        if(inscriptionRepository.existsById(id)) {
            inscriptionRepository.deleteById(id);
        }else{
            throw new RuntimeException("Inscription not found");
        }
    }
}
