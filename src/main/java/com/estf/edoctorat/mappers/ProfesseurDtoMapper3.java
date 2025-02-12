package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.ProfesseurDto3;
import com.estf.edoctorat.models.ProfesseurModel;

public class ProfesseurDtoMapper3 {
    public static ProfesseurDto3 toDto(ProfesseurModel professeur) {
        return ProfesseurDto3.builder()
                .id(professeur.getId())
                .cin(professeur.getCin())
                .telProfesseur(professeur.getTelProfesseur())
                .pathPhoto(professeur.getPathPhoto())
                .grade(professeur.getGrade())
                .numSom(professeur.getNumSom())
                .nombreEncadre(professeur.getNombreEncadre())
                .nombreProposer(professeur.getNombreProposer())
                .etablissement_id(professeur.getEtablissement() != null ? professeur.getEtablissement().getIdEtablissement() : null)
                .etablissementNom(professeur.getEtablissement() != null ? professeur.getEtablissement().getNomEtablissement() : null)
                .labo_id(professeur.getLabo_id())
                .nom(professeur.getUser() != null ? professeur.getUser().getLast_name() : null)
                .prenom(professeur.getUser() != null ? professeur.getUser().getFirst_name() : null)
                .build();
    }
}