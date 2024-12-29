package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.AuthGroupModel;
import com.estf.edoctorat.repositories.AuthGroupRepository;

@Service
public class AuthGroupService {

    @Autowired
    private AuthGroupRepository authGroupRepository;

    public List<AuthGroupModel> getAll() {
        return authGroupRepository.findAll();
    }

    public Optional<AuthGroupModel> getById(long id) {
        return authGroupRepository.findById(id);
    }

    public AuthGroupModel create(AuthGroupModel authGroupModel) {
        return authGroupRepository.save(authGroupModel);
    }

    public AuthGroupModel update(long id, AuthGroupModel authGroupModel) {
        if (authGroupRepository.existsById(id)) {
            authGroupModel.setId(id);
            return authGroupRepository.save(authGroupModel);
        }
        return null;
    }

    public void delete(long id) {
        if (authGroupRepository.existsById(id)) {
            authGroupRepository.deleteById(id);
        } else {
            throw new RuntimeException("auth group not found");
        }
    }

}
