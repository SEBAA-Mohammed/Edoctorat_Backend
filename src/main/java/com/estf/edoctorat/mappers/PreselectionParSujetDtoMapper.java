package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.PreselectionParSujetDto;
import com.estf.edoctorat.models.ExaminerModel;

import java.util.List;

public class PreselectionParSujetDtoMapper {

    public static PreselectionParSujetDto toDto( ExaminerModel examiner ){

        return new PreselectionParSujetDto(
                examiner.getId(),
                CandidatDtoMapper.toDto(examiner.getCandidat()),
                examiner.getValider()
        );

    }

}
