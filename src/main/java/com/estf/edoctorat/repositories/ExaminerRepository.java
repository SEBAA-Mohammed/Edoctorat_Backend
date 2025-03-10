package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.ExaminerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminerRepository extends JpaRepository<ExaminerModel, Long> {

    @Query("select E from ExaminerModel E where E.commission.id in ( select C.id from CommissionModel C where C.laboratoire.id = :labID )")
    Page<ExaminerModel> findByLabo(@Param("labID") Long labID, Pageable pageable);

    @Query("select E from ExaminerModel E where E.sujet.id = :sujetID ")
    List<ExaminerModel> findBySujetID(@Param("sujetID") Long sujetID);
    @Query("SELECT e FROM ExaminerModel e JOIN e.sujet s JOIN s.formationDoctorale f JOIN f.ced c WHERE c.id = :cedId")
    Page<ExaminerModel> findByCedId(@Param("cedId") Long cedId, Pageable pageable);

    @Modifying
    @Query("update ExaminerModel E set E.publier = true where E.publier = false and E.valider = true and E.decision = 'Liste d''attente' ")
    void updatePublierListeAttente();

    @Modifying
    @Query("update ExaminerModel E set E.publier = true where E.publier = false and E.valider = true and E.decision = 'Liste principale' ")
    void updatePublierListePrincipale();

    @Query("SELECT e FROM ExaminerModel e " +
            "JOIN e.commission c " +
            "JOIN c.commissionProfesseurs cp " +
            "WHERE cp.professeur.id = :profId")
    Page<ExaminerModel> findExaminersByProfesseurId(@Param("profId") Long profId, Pageable pageable);
}