package com.estf.edoctorat.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "etablissement")

public class EtablissementModel {
    @Id
    private Long idEtablissement ;

    private String nomEtablissement ;
    @OneToMany
    @JoinColumn(name = "etablissement_id")
    @JsonBackReference
    private List<FormationdoctoraleModel> formationdoctorales;
}
