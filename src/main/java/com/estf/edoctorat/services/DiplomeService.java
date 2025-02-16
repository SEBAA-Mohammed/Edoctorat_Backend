package com.estf.edoctorat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.dto.DiplomeDto;
import com.estf.edoctorat.mappers.DiplomeDtoMapper;
import com.estf.edoctorat.models.DiplomeModel;
import com.estf.edoctorat.repositories.DiplomeRepository;

@Service
public class DiplomeService {

    @Autowired
    private DiplomeRepository diplomeRepository;

    public Page<DiplomeDto> getDiplomesByCandidat(Long candidatId, int limit, int offset) {
        return diplomeRepository.findByCandidatId(
                candidatId,
                PageRequest.of(offset / limit, limit)).map(DiplomeDtoMapper::toDto);
    }

    public DiplomeModel create(DiplomeModel diplome) {
        return diplomeRepository.save(diplome);
    }

    public DiplomeModel update(Long id, DiplomeModel updates) {
        DiplomeModel existing = diplomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplome not found"));

        existing.setIntitule(updates.getIntitule());
        existing.setType(updates.getType());
        existing.setDateCommission(updates.getDateCommission());
        existing.setMention(updates.getMention());
        existing.setPays(updates.getPays());
        existing.setEtablissement(updates.getEtablissement());
        existing.setSpecialite(updates.getSpecialite());
        existing.setVille(updates.getVille());
        existing.setProvince(updates.getProvince());
        existing.setMoyen_generale(updates.getMoyen_generale());

        return diplomeRepository.save(existing);
    }

    public void delete(Long id) {
        diplomeRepository.deleteById(id);
    }
}