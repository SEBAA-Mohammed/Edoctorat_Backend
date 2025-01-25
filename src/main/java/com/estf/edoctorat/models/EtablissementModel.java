package com.estf.edoctorat.models;


import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "etablissement")

public class EtablissementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idEtablissement ;
    private String nomEtablissement ;
    @OneToMany
    @JoinColumn(name = "etablissement_id")
    private List<FormationdoctoraleModel> formationdoctorales;
}
