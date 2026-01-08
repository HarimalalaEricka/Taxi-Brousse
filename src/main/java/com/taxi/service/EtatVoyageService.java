package com.taxi.service;

import com.taxi.models.EtatVoyage;
import com.taxi.repository.EtatVoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtatVoyageService {

    @Autowired
    private EtatVoyageRepository EtatVoyageRepository;

    public EtatVoyage create(EtatVoyage EtatVoyage) {
        return EtatVoyageRepository.save(EtatVoyage);
    }

    public List<EtatVoyage> getAll() {
        return EtatVoyageRepository.findAll();
    }

    public Optional<EtatVoyage> getById(Long id) {
        return EtatVoyageRepository.findById(id);
    }

    public EtatVoyage update(EtatVoyage EtatVoyage) {
        return EtatVoyageRepository.save(EtatVoyage);
    }

    public void delete(Long id) {
        EtatVoyageRepository.deleteById(id);
    }
}
