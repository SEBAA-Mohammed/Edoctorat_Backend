package com.estf.edoctorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommissionCreationDto {

    private Date dateCommission;
    private Date heure;
    private String lieu;
    private List<Long> professeurs;
    private List<Long> sujets_ids;
    private long labo_id;

}
