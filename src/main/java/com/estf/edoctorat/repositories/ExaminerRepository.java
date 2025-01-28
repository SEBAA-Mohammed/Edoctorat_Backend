package com.estf.edoctorat.repositories;

import com.estf.edoctorat.models.ExaminerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExaminerRepository extends JpaRepository<ExaminerModel, Long> {

    @Query("select E from ExaminerModel E where E.commission.id in ( select C.id from CommissionModel C where C.laboratoire.id = :labID )")
    List<ExaminerModel> findByLabo(@Param("labID") Long labID);

    @Query("select E from ExaminerModel E where E.sujet.id = :sujetID ")
    List<ExaminerModel> findBySujetID(@Param("sujetID") Long sujetID);

}