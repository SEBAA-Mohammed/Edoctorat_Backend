package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CandidatPostulerModel;
import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.CandidatPostulerRepository;
import com.estf.edoctorat.repositories.ProfesseurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProfesseurCandidatsService {
    @Autowired
    private CandidatPostulerRepository candidatPostulerRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    public List<CandidatPostulerModel> getProfesseurCandidats(UserModel user) {
        ProfesseurModel professeur = professeurRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User is not a professor"));
        return candidatPostulerRepository.findBySujetProfesseurId(professeur.getId());
    }
}