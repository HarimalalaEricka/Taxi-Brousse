package com.taxi.service;

import com.taxi.models.PlusieurPaiement;
import com.taxi.repository.PlusieurPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlusieurPaiementService {

    @Autowired
    private PlusieurPaiementRepository PlusieurPaiementRepository;

    public PlusieurPaiement create(PlusieurPaiement PlusieurPaiement) {
        return PlusieurPaiementRepository.save(PlusieurPaiement);
    }

    public List<PlusieurPaiement> getAll() {
        return PlusieurPaiementRepository.findAll();
    }

    public Optional<PlusieurPaiement> getById(Long id) {
        return PlusieurPaiementRepository.findById(id);
    }

    public PlusieurPaiement update(PlusieurPaiement PlusieurPaiement) {
        return PlusieurPaiementRepository.save(PlusieurPaiement);
    }

    public void delete(Long id) {
        PlusieurPaiementRepository.deleteById(id);
    }
}
