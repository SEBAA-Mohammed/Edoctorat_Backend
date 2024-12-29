package com.estf.edoctorat.services;

import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import com.estf.edoctorat.repositories.DirecteuPoleCalendrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirecteurPoleCalendrierService {

    @Autowired
    private DirecteuPoleCalendrierRepository directeuPoleCalendrierRepository;

    public List<DirecteurPoleCalendrierModel> getAll() { return directeuPoleCalendrierRepository.findAll(); }

    public Optional<DirecteurPoleCalendrierModel> getById(int id) { return directeuPoleCalendrierRepository.findById(id); }

    public DirecteurPoleCalendrierModel create(DirecteurPoleCalendrierModel directeuPoleCalendrier) { return directeuPoleCalendrierRepository.save(directeuPoleCalendrier); }

    public DirecteurPoleCalendrierModel update(int id, DirecteurPoleCalendrierModel directeuPoleCalendrier) {
        if(directeuPoleCalendrierRepository.existsById(id)) {
            return directeuPoleCalendrierRepository.save(directeuPoleCalendrier);
        }
        return null;
    }

    public void delete(int id) {
        if(directeuPoleCalendrierRepository.existsById(id)) {
            directeuPoleCalendrierRepository.deleteById(id);
        }else {
            throw new RuntimeException("Element introuvable!");
        }
    }

}
