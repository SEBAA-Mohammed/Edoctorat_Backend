package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;

@Entity()
@Data
@Table(name = "candidat")

public class CandidatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cne;
    private String cni;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    private String adresseAr;
    private String adresse;
    private String sexe;
    private String villeDeNaissance;
    private String villeDeNaissanceAr;
    private String ville;
    private Date dateDeNaissance;
    private String typeDeHandicape;
    private String academie;
    private String telCandidat;
    private String pathCv;
    private String pathPhoto;
    private int etatDossier;
    private String situation_familiale;
    @ManyToOne
    @JoinColumn(name = "pays_id")
    private PaysModel pays;
    @Column(name = "fonctionnaire", nullable = false)
    private boolean fonctionnaire = false;
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiplomeModel> diplomes;
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatNotificationModel> notifications;
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatPostulerModel> candidatPostulers;

    @PrePersist
    protected void onCreate() {
        this.fonctionnaire = false;
        this.etatDossier = 0;
    }
}
