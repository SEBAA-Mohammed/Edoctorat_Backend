package com.estf.edoctorat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.UserGroupModel;
import com.estf.edoctorat.repositories.UserGroupRepository;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<UserGroupModel> getAll() {
        return userGroupRepository.findAll();
    }

    public Optional<UserGroupModel> getById(long id) {
        return userGroupRepository.findById(id);
    }

    public UserGroupModel create(UserGroupModel UserGroupModel) {
        return userGroupRepository.save(UserGroupModel);
    }

    public UserGroupModel update(long id, UserGroupModel UserGroupModel) {
        if (userGroupRepository.existsById(id)) {
            UserGroupModel.setId(id);
            return userGroupRepository.save(UserGroupModel);
        }
        return null;
    }

    public void delete(long id) {
        if (userGroupRepository.existsById(id)) {
            userGroupRepository.deleteById(id);
        } else {
            throw new RuntimeException("user group not found");
        }
    }
}
