package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.repositories.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {
    @Autowired
    private CandidatRepository candidatRepository;

//    public List<CandidatModel> candidats;
    public List<CandidatModel> getCandidats() {
        return candidatRepository.findAll();
    }
    public Optional<CandidatModel> getCandidatById(Long id) {
        return candidatRepository.findById(id);
    }
    public List<CandidatModel> getCandidatByName(String name) {
        return candidatRepository.findByNomCandidatAr(name);
    }
}
