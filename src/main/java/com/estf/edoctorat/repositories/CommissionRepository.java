package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.CommissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<CommissionModel, Long> {
}
