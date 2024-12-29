package com.estf.edoctorat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.CandidatNotificationModel;

@Repository

public interface CandidatNotificationRepository extends JpaRepository<CandidatNotificationModel, Long> {
}