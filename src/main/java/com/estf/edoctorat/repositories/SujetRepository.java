package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.SujetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SujetRepository extends JpaRepository<SujetModel, Long> {
}
