package com.estf.edoctorat.services;

import com.estf.edoctorat.models.PaysModel;
import com.estf.edoctorat.repositories.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaysService {

    @Autowired
    private PaysRepository paysRepository;

    public PaysModel createPays(PaysModel pays) {
        return paysRepository.save(pays);
    }

    public List<PaysModel> getAllPays() {
        return paysRepository.findAll();
    }

    public Optional<PaysModel> getPaysById(Long id) {
        return paysRepository.findById(id);
    }

    public PaysModel updatePays(Long id, PaysModel updatedPays) {
        return paysRepository.findById(id).map(pays -> {
            pays.setNom(updatedPays.getNom());
            return paysRepository.save(pays);
        }).orElseThrow(() -> new RuntimeException("Pays not found with id " + id));
    }

    public void deletePays(Long id) {
        if (!paysRepository.existsById(id)) {
            throw new RuntimeException("Pays not found with id " + id);
        }
        paysRepository.deleteById(id);
    }
}
