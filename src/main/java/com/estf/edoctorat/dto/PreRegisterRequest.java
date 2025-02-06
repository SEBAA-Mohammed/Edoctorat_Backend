package com.estf.edoctorat.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreRegisterRequest {
    private String email;
    private String nom;
    private String prenom;
    private String origin;
}