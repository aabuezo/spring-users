package com.example.users.services;

import com.example.users.entities.Role;
import com.example.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public List<Role> getRoles() {
        return repository.findAll();
    }

    public Role createRole(Role role) {
        return repository.save(role);
    }

    public Role updateRole(Integer roleId, Role role) {
        Optional<Role> roleToUpdate = repository.findById(roleId);
        if (roleToUpdate.isPresent()) {
            return repository.save(role);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Role %d does not exist", roleId));
        }
    }

    public void deleteRole(Integer roleId) {
        Optional<Role> roleToDelete = repository.findById(roleId);
        if (roleToDelete.isPresent()) {
            repository.delete(roleToDelete.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Role with id %d not found.", roleId));
        }
    }
}
