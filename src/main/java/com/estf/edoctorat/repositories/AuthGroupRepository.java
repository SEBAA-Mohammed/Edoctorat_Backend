package com.estf.edoctorat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estf.edoctorat.models.AuthGroupModel;

@Repository


public interface AuthGroupRepository extends JpaRepository<AuthGroupModel, Long> {
}