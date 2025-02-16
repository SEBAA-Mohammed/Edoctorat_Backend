package com.estf.edoctorat.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String type;
    private SujetDto sujet;
    private CommissionDto commission;
    private Long candidatId;
}