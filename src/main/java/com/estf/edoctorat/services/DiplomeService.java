package com.estf.edoctorat.services;

import com.estf.edoctorat.models.DiplomeModel;
import com.estf.edoctorat.repositories.DiplomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiplomeService {
    @Autowired
    private DiplomeRepository diplomeRepository;

    public List<DiplomeModel> getAllDiplomes() {
        return diplomeRepository.findAll();
    }

    public Optional<DiplomeModel> getDiplomeById(long id) {
        return diplomeRepository.findById(id);
    }

    public DiplomeModel createDiplome(DiplomeModel diplomeModel) {
        return diplomeRepository.save(diplomeModel);
    }

    public DiplomeModel updateDiplomeById(long id, DiplomeModel diplomeModel) {
        if(diplomeRepository.existsById(id)) {
            diplomeModel.setId(id);
            return diplomeRepository.save(diplomeModel);
        }
        return null;
    }

    public void deleteDiplomeById(long id) {
        if(diplomeRepository.existsById(id)){
            diplomeRepository.deleteById(id);
        }else{
            throw new RuntimeException("Diplome not found");
        }
    }

}
