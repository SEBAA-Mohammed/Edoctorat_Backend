package com.estf.edoctorat.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity()
@Data
@Table(name = "candidat_notification")
public class CandidatNotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 255)
    private String type;
//    @OneToMany
//    @JoinColumn(name = "candidat_id")
//    private List<CandidatModel> candidats;
//
//    @OneToMany
//    @JoinColumn(name = "commission_id")
//    private List<CommissionModel> commissions;
//
//    @OneToMany
//    @JoinColumn(name = "sujet_id")
//    private List<SujetModel> sujets;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatModel candidat;

    @ManyToOne
    @JoinColumn(name = "commission_id", nullable = false)
    private CommissionModel commission;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = false)
    private SujetModel sujet;

}
