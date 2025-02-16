package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import com.estf.edoctorat.dto.ProfesseurDto3;
import com.estf.edoctorat.mappers.ProfesseurDtoMapper3;
import com.estf.edoctorat.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.estf.edoctorat.models.ProfesseurModel;
import com.estf.edoctorat.repositories.ProfesseurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProfesseurService {
    @Autowired
    private ProfesseurRepository professeurRepository;

    public Page<ProfesseurDto3> getAll(int limit, int offset) {
        Page<ProfesseurModel> professeurPage = professeurRepository.findAll(PageRequest.of(offset / limit, limit));

        // Convert each ProfesseurModel to ProfesseurDto3
        List<ProfesseurDto3> professeurDTOs = professeurPage.getContent()
                .stream()
                .map(profModel -> ProfesseurDtoMapper3.toDto(profModel))  // Using lambda instead of method reference
                .toList();

        return new PageImpl<>(
                professeurDTOs,
                professeurPage.getPageable(),
                professeurPage.getTotalElements()
        );
    }

    public Optional<ProfesseurModel> getById(long id) {
        return professeurRepository.findById(id);
    }

    public ProfesseurModel create(ProfesseurModel ProfesseurModel) {
        return professeurRepository.save(ProfesseurModel);
    }

    public ProfesseurModel update(long id, ProfesseurModel ProfesseurModel) {
        if (professeurRepository.existsById(id)) {
            ProfesseurModel.setId(id);
            return professeurRepository.save(ProfesseurModel);
        }
        return null;
    }

    public void delete(long id) {
        if (professeurRepository.existsById(id)) {
            professeurRepository.deleteById(id);
        } else {
            throw new RuntimeException("professeur not found");
        }
    }

    public Optional<ProfesseurModel> getByUser(UserModel user) { return professeurRepository.findByUser(user); }

    public Page<ProfesseurModel> getProfesseursByLabID(long id, int limit, int offset){ return professeurRepository.findByLabo_id(id, PageRequest.of(offset / limit, limit)); }

    public List<ProfesseurModel> getProfesseursByLabID(long id){ return professeurRepository.findByLabo_id(id); }
}
