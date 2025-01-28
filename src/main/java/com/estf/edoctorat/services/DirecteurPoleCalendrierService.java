package com.estf.edoctorat.services;

import com.estf.edoctorat.models.DirecteurPoleCalendrierModel;
import com.estf.edoctorat.repositories.DirecteuPoleCalendrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Optional<DirecteurPoleCalendrierModel> updateCalendrier(int calendrierId, Date dateDebut, Date dateFin) {
        return directeuPoleCalendrierRepository.findById(calendrierId).map(calendrier -> {
            calendrier.setDateDebut(dateDebut);
            calendrier.setDateFin(dateFin);
            return directeuPoleCalendrierRepository.save(calendrier);
        });
    }

}
