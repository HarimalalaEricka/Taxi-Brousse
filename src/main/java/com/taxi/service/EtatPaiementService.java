package com.taxi.service;

import com.taxi.models.EtatPaiement;
import com.taxi.repository.EtatPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtatPaiementService {

    @Autowired
    private EtatPaiementRepository EtatPaiementRepository;

    public EtatPaiement create(EtatPaiement EtatPaiement) {
        return EtatPaiementRepository.save(EtatPaiement);
    }

    public List<EtatPaiement> getAll() {
        return EtatPaiementRepository.findAll();
    }

    public Optional<EtatPaiement> getById(Long id) {
        return EtatPaiementRepository.findById(id);
    }

    public EtatPaiement update(EtatPaiement EtatPaiement) {
        return EtatPaiementRepository.save(EtatPaiement);
    }

    public void delete(Long id) {
        EtatPaiementRepository.deleteById(id);
    }
}
