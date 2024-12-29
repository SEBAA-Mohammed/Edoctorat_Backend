package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CedModel;
import com.estf.edoctorat.models.CommissionProfesseurModel;
import com.estf.edoctorat.repositories.CommissionProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionProfesseurService {

    @Autowired
    private CommissionProfesseurRepository commissionProfesseurRepository;

    public List<CommissionProfesseurModel> getAll() { return commissionProfesseurRepository.findAll(); }

    public Optional<CommissionProfesseurModel> getById(long id) { return commissionProfesseurRepository.findById(id); }

    public CommissionProfesseurModel create(CommissionProfesseurModel commissionProfesseur) { return commissionProfesseurRepository.save(commissionProfesseur); }

    public CommissionProfesseurModel update(long id, CommissionProfesseurModel commissionProfesseur) {
        if(commissionProfesseurRepository.existsById(id)) {
            return commissionProfesseurRepository.save(commissionProfesseur);
        }
        return null;
    }

    public void delete(long id) {
        if(commissionProfesseurRepository.existsById(id)) {
            commissionProfesseurRepository.deleteById(id);
        }else {
            throw new RuntimeException("Element introuvable!");
        }
    }

}
