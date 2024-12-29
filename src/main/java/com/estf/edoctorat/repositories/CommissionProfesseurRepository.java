package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CommissionProfesseurModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionProfesseurRepository extends JpaRepository<CommissionProfesseurModel, Long> {
}
