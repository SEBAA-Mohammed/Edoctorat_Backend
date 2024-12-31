package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.LaboratoireModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoireRepository extends JpaRepository<LaboratoireModel, Long> {
}
