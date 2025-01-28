package com.estf.edoctorat.services;

import com.estf.edoctorat.models.CedModel;
import com.estf.edoctorat.models.CommissionModel;
import com.estf.edoctorat.repositories.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommissionService {
    @Autowired
    private CommissionRepository commissionRepository;

    public List<CommissionModel> getAll() { return commissionRepository.findAll(); }

    public Optional<CommissionModel> getById(long id) { return commissionRepository.findById(id); }

    public CommissionModel create(CommissionModel commission) { return commissionRepository.save(commission); }

    public CommissionModel update(long id, CommissionModel commission) {
        if(commissionRepository.existsById(id)) {
            return commissionRepository.save(commission);
        }
        return null;
    }

    public void delete(long id) {
        if(commissionRepository.existsById(id)) {
            commissionRepository.deleteById(id);
        }else {
            throw new RuntimeException("Commission introuvable!");
        }
    }

    public List<CommissionModel> getByLabID(long id) { return commissionRepository.findByLaboratoire_Id(id); }

}
