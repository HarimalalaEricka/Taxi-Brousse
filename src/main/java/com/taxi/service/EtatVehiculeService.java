package com.taxi.service;

import com.taxi.models.EtatVehicule;
import com.taxi.repository.EtatVehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtatVehiculeService {

    @Autowired
    private EtatVehiculeRepository EtatVehiculeRepository;

    public EtatVehicule create(EtatVehicule EtatVehicule) {
        return EtatVehiculeRepository.save(EtatVehicule);
    }

    public List<EtatVehicule> getAll() {
        return EtatVehiculeRepository.findAll();
    }

    public Optional<EtatVehicule> getById(Long id) {
        return EtatVehiculeRepository.findById(id);
    }

    public EtatVehicule update(EtatVehicule EtatVehicule) {
        return EtatVehiculeRepository.save(EtatVehicule);
    }

    public void delete(Long id) {
        EtatVehiculeRepository.deleteById(id);
    }
}
