package com.estf.edoctorat.services;

import com.estf.edoctorat.models.FormationdoctoraleModel;
import com.estf.edoctorat.repositories.FormationdoctoraleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationdoctoraleService {

    @Autowired
    private FormationdoctoraleRepository formationdoctoraleRepository;

    public List<FormationdoctoraleModel> getAll() { return formationdoctoraleRepository.findAll(); }

    public Optional<FormationdoctoraleModel> getById(long id) { return formationdoctoraleRepository.findById(id); }

    public FormationdoctoraleModel create(FormationdoctoraleModel formationdoctorale) { return formationdoctoraleRepository.save(formationdoctorale); }

    public FormationdoctoraleModel update(long id, FormationdoctoraleModel formationdoctorale) {
        if(formationdoctoraleRepository.existsById(id)) {
            return formationdoctoraleRepository.save(formationdoctorale);
        }
        return null;
    }

    public void delete(long id) {
        if(formationdoctoraleRepository.existsById(id)) {
            formationdoctoraleRepository.deleteById(id);
        }else {
            throw new RuntimeException("Formation doctorale introuvable!");
        }
    }

}
