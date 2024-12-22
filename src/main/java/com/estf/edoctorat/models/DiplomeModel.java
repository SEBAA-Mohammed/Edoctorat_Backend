package com.estf.edoctorat.models;


import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity()
@Data
public class DiplomeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String intitule;
    private String type;
    private Date dateCommission;
    private String mentien;
    private String pays;
    private String etablissement;
    private String specialite;
    private String ville;
    private String province;
    private double moyen_generale;
    @ManyToOne
    @JoinColumn(name="candidat_id", nullable=false)
    private CandidatModel candidat;
    @OneToMany(mappedBy = "diplome", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnexeModel> annexes;
}
