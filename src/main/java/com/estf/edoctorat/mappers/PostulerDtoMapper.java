package com.estf.edoctorat.mappers;


import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.models.CandidatPostulerModel;

public class PostulerDtoMapper {

    public static PostulerDto toDto(CandidatPostulerModel postuler) {

        return new PostulerDto(

                postuler.getPathFile(),
                SujetDtoMapper.toDto(postuler.getSujet()),
                CandidatDtoMapper.toDto(postuler.getCandidat()),
                postuler.getId()

        );

    }

}
