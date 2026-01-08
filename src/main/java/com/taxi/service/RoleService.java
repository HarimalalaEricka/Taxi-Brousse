package com.taxi.service;

import com.taxi.models.Role;
import com.taxi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository RoleRepository;

    public Role create(Role Role) {
        return RoleRepository.save(Role);
    }

    public List<Role> getAll() {
        return RoleRepository.findAll();
    }

    public Optional<Role> getById(Long id) {
        return RoleRepository.findById(id);
    }

    public Role update(Role Role) {
        return RoleRepository.save(Role);
    }

    public void delete(Long id) {
        RoleRepository.deleteById(id);
    }
}
