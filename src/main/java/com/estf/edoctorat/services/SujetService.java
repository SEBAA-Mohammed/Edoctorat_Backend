package com.estf.edoctorat.services;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public SujetModel create(SujetModel sujetModel , UserModel currentUser) {
        sujetModel.setProfesseur(currentUser.getProfesseur());
        sujetModel.setFormationDoctorale((FormationdoctoraleModel) currentUser.getProfesseur().getEtablissement().getFormationdoctorales());
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

    public Page<SujetModel> getSujetsByProfID(long id, int limit, int offset) { return sujetRepository.findSujetByProfesseur_Id(id, PageRequest.of(offset/limit, limit)); }


    public List<SujetModel> getSujetsByProfID(long id) { return sujetRepository.findSujetByProfesseur_Id(id); }

    public Page<SujetModel> getSujetByCed(UserModel currentUser, int limit, int offset) {
        long idCed = currentUser.getProfesseur().getCed().getId();

        return sujetRepository.findByCedId(idCed, PageRequest.of(offset / limit, limit));
    }
}
