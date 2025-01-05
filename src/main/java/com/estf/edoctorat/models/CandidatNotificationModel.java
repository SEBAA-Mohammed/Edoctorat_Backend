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
    @OneToMany(mappedBy = "candidatNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatModel> candidats;
    // Long commision_id; // a faire
    @OneToMany(mappedBy = "candidatNotification", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<CommissionModel> commissions;
    // Long sujet_id; // a faire
    @OneToMany(mappedBy = "candidatNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SujetModel> sujets;
}
