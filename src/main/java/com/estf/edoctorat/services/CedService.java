package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CedModel;
import com.estf.edoctorat.repositories.CedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CedService {

    @Autowired
    private CedRepository cedRepository;

    public List<CedModel> getAll() { return cedRepository.findAll(); }

    public Optional<CedModel> getById(long id) { return cedRepository.findById(id); }

    public CedModel create(CedModel ced) { return cedRepository.save(ced); }

    public CedModel update(long id, CedModel ced) {
        if(cedRepository.existsById(id)) {
            return cedRepository.save(ced);
        }
        return null;
    }

    public void delete(long id) {
        if(cedRepository.existsById(id)) {
            cedRepository.deleteById(id);
        }else {
            throw new RuntimeException("CED introuvable!");
        }
    }
}
