package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirecteuPoleCalendrierRepository extends JpaRepository<DirecteurPoleCalendrierModel, Integer> {
}
