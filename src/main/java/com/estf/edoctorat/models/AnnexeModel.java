package com.estf.edoctorat.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity()
@Data
public class AnnexeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String typeAnnexe;
    private String Titre;
    private String pathFile;
    @ManyToOne
    @JoinColumn(name = "diplome_id", nullable = false)
    private DiplomeModel diplome;
}
