package com.estf.edoctorat.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonManagedReference
    private List<CommissionProfesseurModel> commissionProfesseurs;
    @OneToMany(mappedBy = "commission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatNotificationModel> notifications;


    public CommissionModel(Date dateCommission, Date heure, String lieu, LaboratoireModel laboratoire, List<CommissionProfesseurModel> commissionProfesseurs){
        this.dateCommission = dateCommission;
        this.heure = heure;
        this.lieu = lieu;
        this.laboratoire = laboratoire;
        this.commissionProfesseurs = commissionProfesseurs;
        this.notifications = new ArrayList<>();
    }


}
