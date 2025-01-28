package com.estf.edoctorat.mappers;


import com.estf.edoctorat.dto.CommissionCreationDto;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.models.LaboratoireModel;
import com.estf.edoctorat.services.CommissionService;
import com.estf.edoctorat.services.LaboratoireService;

import java.util.ArrayList;

public class CommissionDtoMapper {


    public static CommissionModel toCommission(CommissionCreationDto dto, LaboratoireService laboServ){

        LaboratoireModel labo = laboServ.getById(dto.getLabo_id()).get();

        return new CommissionModel(
                dto.getDateCommission(),
                dto.getHeure(),
                dto.getLieu(),
                labo,
                new ArrayList<>()
        );
    }

}
