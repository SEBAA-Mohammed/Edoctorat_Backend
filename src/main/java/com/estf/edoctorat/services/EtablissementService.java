package com.estf.edoctorat.services;

import com.estf.edoctorat.models.EtablissementModel;
import com.estf.edoctorat.repositories.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtablissementService {
    @Autowired
    private EtablissementRepository etablissementRepository;

    public List<EtablissementModel> getAll() {
        return etablissementRepository.findAll();
    }

    public Optional<EtablissementModel> getById(long id) {
        return etablissementRepository.findById(id);
    }

    public EtablissementModel create(EtablissementModel EtablissementModel) {
        return etablissementRepository.save(EtablissementModel);
    }

    public EtablissementModel update(long id, EtablissementModel etablissementModel) {
        if (etablissementRepository.existsById(id)) {
            etablissementModel.setIdEtablissement(id);
            return etablissementRepository.save(etablissementModel);
        }
        return null;
    }

    public void deleteAnnexe(long id) {
        if(etablissementRepository.existsById(id)) {
            etablissementRepository.deleteById(id);
        }else{
            throw new RuntimeException("etablissement not found");
        }
    }
}
