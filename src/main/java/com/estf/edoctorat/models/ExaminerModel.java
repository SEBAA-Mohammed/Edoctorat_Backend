package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "examiner")

public class ExaminerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(nullable = true)
    private String decision ;
    private float noteDossier ;
    @Column(length = 11, nullable = false)
    private int noteEntretien ;
    private byte publier ;
    @OneToOne
    @JoinColumn(name = "commission_id")
    private CommissionModel commission ;
    @OneToOne
    @JoinColumn(name = "sujet_id")
    private SujetModel sujet ;
    @OneToOne
    @JoinColumn(name = "candidat_id")
    private CandidatModel candidat ;
    private byte valider ;
}
