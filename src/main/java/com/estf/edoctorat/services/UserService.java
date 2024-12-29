package com.estf.edoctorat.services;


import com.estf.edoctorat.models.UserModel;
import com.estf.edoctorat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel updateUser(Long id, UserModel updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(updatedUser.getPassword());
            user.setLast_login(updatedUser.getLast_login());
            user.setIs_superuser(updatedUser.getIs_superuser());
            user.setUsername(updatedUser.getUsername());
            user.setFirst_name(updatedUser.getFirst_name());
            user.setLast_name(updatedUser.getLast_name());
            user.setEmail(updatedUser.getEmail());
            user.setIs_staff(updatedUser.getIs_staff());
            user.setIs_active(updatedUser.getIs_active());
            user.setDate_joined(updatedUser.getDate_joined());

            if (updatedUser.getUserGroup() != null && updatedUser.getUserGroup().getId() != null) {
                user.setUserGroup(updatedUser.getUserGroup());
            } else {
                user.setUserGroup(null);
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}
