package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.repositories.CandidatPostulerRepository;

@Service
public class CandidatPostulerService {
    @Autowired
    private CandidatPostulerRepository candidatPostulerRepository;

    public List<CandidatPostulerModel> getAll() {
        return candidatPostulerRepository.findAll();
    }

    public Optional<CandidatPostulerModel> getById(long id) {
        return candidatPostulerRepository.findById(id);
    }

    public CandidatPostulerModel create(CandidatPostulerModel candidatPostulerModel) {
        return candidatPostulerRepository.save(candidatPostulerModel);
    }

    public CandidatPostulerModel update(long id, CandidatPostulerModel candidatPostulerModel) {
        if (candidatPostulerRepository.existsById(id)) {
            candidatPostulerModel.setId(id);
            return candidatPostulerRepository.save(candidatPostulerModel);
        }
        return null;
    }

    public void delete(long id) {
        if (candidatPostulerRepository.existsById(id)) {
            candidatPostulerRepository.deleteById(id);
        } else {
            throw new RuntimeException("candidat postuler not found");
        }
    }

    public Page<CandidatPostulerModel> getByCandidatId(Long candidatId, Pageable pageable) {
        return candidatPostulerRepository.findByCandidatId(candidatId, pageable);
    }

    public Page<CandidatPostulerModel> getAll(int limit, int offset) {
        return candidatPostulerRepository.findAll(PageRequest.of(offset / limit, limit));
    }

}