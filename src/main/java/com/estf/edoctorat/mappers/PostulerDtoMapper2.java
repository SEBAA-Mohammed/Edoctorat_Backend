package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.dto.PostulerDto;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.CandidatPostulerModel;

import java.util.List;
import java.util.stream.Collectors;

public class PostulerDtoMapper2 {
    public static PostulerDto toDto(CandidatPostulerModel postuler) {
        return new PostulerDto(
                postuler.getPathFile(),
                SujetDtoMapper.toDto(postuler.getSujet()),
                toCandidatDto(postuler.getCandidat()),
                postuler.getId()
        );
    }

    public static CandidatDto toCandidatDto(CandidatModel candidat) {
        return new CandidatDto(
                candidat.getId(),
                candidat.getCne(),
                candidat.getPays().getNom(),
                candidat.getUser().getFirst_name(),
                candidat.getUser().getLast_name(),
                candidat.getUser().getEmail(),
                candidat.getCni(),
                candidat.getNomCandidatAr(),
                candidat.getPrenomCandidatAr(),
                candidat.getAdresse(),
                candidat.getAdresseAr(),
                candidat.getSexe(),
                candidat.getVilleDeNaissance(),
                candidat.getVilleDeNaissanceAr(),
                candidat.getVille(),
                candidat.getDateDeNaissance().toString(),
                candidat.getTypeDeHandicape(),
                candidat.getAcademie(),
                candidat.getTelCandidat(),
                candidat.getPathCv(),
                candidat.getPathPhoto(),
                candidat.getEtatDossier(),
                candidat.getSituation_familiale(),
                candidat.getFonctionaire() ? "Oui" : "Non"
        );
    }

    public static List<PostulerDto> toDtoList(List<CandidatPostulerModel> postulers) {
        return postulers.stream()
                .map(PostulerDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}

