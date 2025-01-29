package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionDto {

    private Long id;
    private Date dateCommission;
    private Date heure;
    private boolean valider;
    private String lieu;
    private Long labo;
    private List<ProfesseurDto> participants;
    private List<SujetDto> sujets;

}
