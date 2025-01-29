package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.ExaminerDto;
import com.estf.edoctorat.models.ExaminerModel;

public class ExaminerDtoMapper {

    public static ExaminerDto toDto(ExaminerModel examiner) {

        String cne = examiner.getCandidat().getCne();

        return new ExaminerDto(
                examiner.getId(),
                SujetDtoMapper.toDto(examiner.getSujet()),
                cne,
                examiner.getNoteDossier(),
                examiner.getNoteEntretien(),
                examiner.getDecision(),
                examiner.getCommission().getId(),
                CandidatDtoMapper.toDto(examiner.getCandidat()),
                examiner.getPublier()
        );

    }

}
