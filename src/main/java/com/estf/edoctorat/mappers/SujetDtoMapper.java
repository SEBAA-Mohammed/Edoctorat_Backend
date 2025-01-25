package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.models.SujetModel;

public class SujetDtoMapper {

    public static SujetDto toDto(SujetModel sujet){

        return new SujetDto(
                sujet.getId(),
                sujet.getTitre(),
                sujet.getDescription(),
                sujet.getPublier(),
                sujet.getCodirecteur() != null ? ProfesseurDtoMapper.toDto(sujet.getCodirecteur()) : null,
                sujet.getFormationDoctorale(),
                ProfesseurDtoMapper.toDto(sujet.getProfesseur())
        );

    }

}
