package com.estf.edoctorat.services;

import com.estf.edoctorat.models.InscriptionModel;
import com.estf.edoctorat.repositories.InscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PoleAllInscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public Page<InscriptionModel> getAllInscriptions(int limit, int offset) {
        return inscriptionRepository.findAll(PageRequest.of(offset / limit, limit));
    }
}
