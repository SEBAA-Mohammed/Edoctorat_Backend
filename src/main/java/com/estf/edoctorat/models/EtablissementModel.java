package com.estf.edoctorat.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "etablissement")

public class EtablissementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtablissement ;
    private String nomEtablissement ;
}
