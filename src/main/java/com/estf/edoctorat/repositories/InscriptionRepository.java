package com.estf.edoctorat.repositories;


import com.estf.edoctorat.models.InscriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionModel, Long> {
}
