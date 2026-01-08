package com.taxi.service;

import com.taxi.models.Facture;
import com.taxi.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {

    @Autowired
    private FactureRepository FactureRepository;

    public Facture create(Facture Facture) {
        return FactureRepository.save(Facture);
    }

    public List<Facture> getAll() {
        return FactureRepository.findAll();
    }

    public Optional<Facture> getById(Long id) {
        return FactureRepository.findById(id);
    }

    public Facture update(Facture Facture) {
        return FactureRepository.save(Facture);
    }

    public void delete(Long id) {
        FactureRepository.deleteById(id);
    }
}
