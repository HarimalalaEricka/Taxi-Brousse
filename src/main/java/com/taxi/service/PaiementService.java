package com.taxi.service;

import com.taxi.models.Paiement;
import com.taxi.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository PaiementRepository;

    public Paiement create(Paiement Paiement) {
        return PaiementRepository.save(Paiement);
    }

    public List<Paiement> getAll() {
        return PaiementRepository.findAll();
    }

    public Optional<Paiement> getById(Long id) {
        return PaiementRepository.findById(id);
    }

    public Paiement update(Paiement Paiement) {
        return PaiementRepository.save(Paiement);
    }

    public void delete(Long id) {
        PaiementRepository.deleteById(id);
    }
}
