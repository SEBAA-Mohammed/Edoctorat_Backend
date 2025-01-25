package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "commission")
public class CommissionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "DATE", nullable = false)
    private Date dateCommission;
    @Column(nullable = false)
    private String lieu;
    @Column(columnDefinition = "TIME(6)", nullable = false)
    private Date heure;
    @OneToOne
    @JoinColumn(name = "labo_id")
    private LaboratoireModel laboratoire;
    @OneToMany(mappedBy = "commission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommissionProfesseurModel> commissionProfesseurs;
    @ManyToOne
    @JoinColumn(name = "candidat_notification_id")
    private CandidatNotificationModel candidatNotification;
}
