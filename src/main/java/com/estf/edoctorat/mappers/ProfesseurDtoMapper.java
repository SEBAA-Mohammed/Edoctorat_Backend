package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.ProfesseurDto;
import com.estf.edoctorat.models.ProfesseurModel;

public class ProfesseurDtoMapper {

    public static ProfesseurDto toDto(ProfesseurModel professeur){

        return new ProfesseurDto(
                professeur.getId(),
                professeur.getUser().getFirst_name(),
                professeur.getUser().getLast_name()
        );
    }

}
