package com.estf.edoctorat.mappers;

import com.estf.edoctorat.dto.CandidatDto;
import com.estf.edoctorat.models.CandidatModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.services.UserService;

public class CandidatDtoMapper {

    public static CandidatDto toDto(CandidatModel candidat){

        UserModel user = candidat.getUser();

        return new CandidatDto(
                candidat.getId(),
                candidat.getCne(),
                candidat.getPays().getNom(),
                user.getLast_name(),
                user.getLast_name(),
                user.getEmail(),
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
                candidat.getFonctionaire().toString()
        );

    }

}
