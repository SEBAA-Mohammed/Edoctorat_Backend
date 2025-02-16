package com.estf.edoctorat.repositories;


import com.estf.edoctorat.models.InscriptionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionModel, Long> {
    @Query("SELECT i FROM InscriptionModel i JOIN i.sujet s WHERE s.professeur.id = :profId")
    Page<InscriptionModel> findBySujetProfesseurId(@Param("profId") Long profId, Pageable pageable);
}
