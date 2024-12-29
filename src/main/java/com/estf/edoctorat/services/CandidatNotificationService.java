package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.CandidatNotificationModel;
import com.estf.edoctorat.repositories.CandidatNotificationRepository;

@Service
public class CandidatNotificationService {
    @Autowired
    private CandidatNotificationRepository candidatNotificationRepository;

    public List<CandidatNotificationModel> getAll() {
        return candidatNotificationRepository.findAll();
    }

    public Optional<CandidatNotificationModel> getById(long id) {
        return candidatNotificationRepository.findById(id);
    }

    public CandidatNotificationModel create(CandidatNotificationModel candidatNotificationModel) {
        return candidatNotificationRepository.save(candidatNotificationModel);
    }

    public CandidatNotificationModel update(long id, CandidatNotificationModel candidatNotificationModel) {
        if (candidatNotificationRepository.existsById(id)) {
            candidatNotificationModel.setId(id);
            return candidatNotificationRepository.save(candidatNotificationModel);
        }
        return null;
    }

    public void delete(long id) {
        if (candidatNotificationRepository.existsById(id)) {
            candidatNotificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("candidat notification not found");
        }
    }
}
