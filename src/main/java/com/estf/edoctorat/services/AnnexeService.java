package com.estf.edoctorat.services;

import com.estf.edoctorat.models.AnnexeModel;
import com.estf.edoctorat.repositories.AnnexeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnexeService {
    @Autowired
    private AnnexeRepository annexeRepository;

    public List<AnnexeModel> getAllAnnexes() {
        return annexeRepository.findAll();
    }

    public Optional<AnnexeModel> getAnnexeById(long id) {
        return annexeRepository.findById(id);
    }

    public AnnexeModel createAnnexe(AnnexeModel annexeModel) {
        return annexeRepository.save(annexeModel);
    }

    public AnnexeModel updateAnnexe(long id, AnnexeModel annexeModel) {
        if (annexeRepository.existsById(id)) {
            annexeModel.setId(id);
            return annexeRepository.save(annexeModel);
        }
        return null;
    }

    public void deleteAnnexe(long id) {
        if(annexeRepository.existsById(id)) {
            annexeRepository.deleteById(id);
        }else{
            throw new RuntimeException("Annexe not found");
        }
    }

}
