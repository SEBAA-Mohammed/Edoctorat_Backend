package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity()
@Data
@Table(name = "annexe")

public class AnnexeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String typeAnnexe;
    private String titre;
    private String pathFile;
    @ManyToOne
    @JoinColumn(name = "diplome", nullable = false)
    private DiplomeModel diplome;
}
