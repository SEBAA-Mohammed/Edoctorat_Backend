package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.dto.SujetDto;
import com.estf.edoctorat.models.SujetModel;

public class SujetDtoMapper {

    public static SujetDto toDto(SujetModel sujet){

        return new SujetDto(
                sujet.getId(),
                ProfesseurDtoMapper.toDto(sujet.getProfesseur()),
                FormationdoctoraleDtoMapper.toDto(sujet.getFormationDoctorale()),
                sujet.getTitre(),
                sujet.getCodirecteur() != null ? ProfesseurDtoMapper.toDto(sujet.getCodirecteur()) : null,
                sujet.getDescription(),
                sujet.getPublier()
        );

    }

}
