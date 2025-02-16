package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.dto.NotificationDto;
import com.estf.edoctorat.mappers.CommissionDtoMapper;
import com.estf.edoctorat.mappers.SujetDtoMapper;
import com.estf.edoctorat.models.CandidatNotificationModel;
import com.estf.edoctorat.repositories.CandidatNotificationRepository;

@Service
public class CandidatNotificationService {
    @Autowired
    private CandidatNotificationRepository candidatNotificationRepository;
    @Autowired
    private SujetService sujetService;

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

    public Page<NotificationDto> getCandidatNotifications(Long candidatId, int limit, int offset) {
        Page<CandidatNotificationModel> notifications = candidatNotificationRepository
                .findAll(PageRequest.of(offset / limit, limit));

        return notifications.map(notification -> mapToNotificationDto(notification));
    }

    private NotificationDto mapToNotificationDto(CandidatNotificationModel notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getType(),
                SujetDtoMapper.toDto(notification.getSujet()),
                CommissionDtoMapper.toDto(notification.getCommission(), sujetService),
                notification.getCandidat().getId());
    }
}
