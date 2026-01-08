package com.taxi.service;

import com.taxi.models.Vehicule;
import com.taxi.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository VehiculeRepository;

    public Vehicule create(Vehicule Vehicule) {
        return VehiculeRepository.save(Vehicule);
    }

    public List<Vehicule> getAll() {
        return VehiculeRepository.findAll();
    }

    public Optional<Vehicule> getById(Long id) {
        return VehiculeRepository.findById(id);
    }

    public Vehicule update(Vehicule Vehicule) {
        return VehiculeRepository.save(Vehicule);
    }

    public void delete(Long id) {
        VehiculeRepository.deleteById(id);
    }
}
