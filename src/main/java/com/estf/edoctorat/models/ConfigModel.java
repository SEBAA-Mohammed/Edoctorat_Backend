package com.estf.edoctorat.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "config")
public class ConfigModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int max_sujet_postuler;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_debut_postuler_sujet_candidat;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_debut_modifier_sujet_prof;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_fin_postuler_sujet_candidat;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_fin_modifier_sujet_prof;
}