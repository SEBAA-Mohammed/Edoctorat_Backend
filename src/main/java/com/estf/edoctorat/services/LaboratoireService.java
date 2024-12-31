package com.estf.edoctorat.services;

import com.estf.edoctorat.models.LaboratoireModel;
import com.estf.edoctorat.repositories.LaboratoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoireService {
    @Autowired
    private LaboratoireRepository laboratoireRepository;

    public List<LaboratoireModel> getAll() {
        return laboratoireRepository.findAll();
    }

    public Optional<LaboratoireModel> getById(long id) {
        return laboratoireRepository.findById(id);
    }

    public LaboratoireModel create(LaboratoireModel laboratoireModel) {
        return laboratoireRepository.save(laboratoireModel);
    }

    public LaboratoireModel update(long id, LaboratoireModel laboratoireModel) {
        if (laboratoireRepository.existsById(id)) {
            laboratoireModel.setId(id);
            return laboratoireRepository.save(laboratoireModel);
        }
        return null;
    }

    public void delete(long id) {
        if(laboratoireRepository.existsById(id)) {
            laboratoireRepository.deleteById(id);
        }else{
            throw new RuntimeException("laboratoire not found");
        }
    }
}
