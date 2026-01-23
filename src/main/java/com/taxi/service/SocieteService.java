package com.taxi.service;

import com.taxi.models.Societe;
import com.taxi.repository.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {

    @Autowired
    private SocieteRepository societeRepository;

    public Societe create(Societe societe) {
        return societeRepository.save(societe);
    }

    public List<Societe> getAll() {
        return societeRepository.findAll();
    }

    public Optional<Societe> getById(Long id) {
        return societeRepository.findById(id);
    }

    public Societe update(Societe societe) {
        return societeRepository.save(societe);
    }

    public void delete(Long id) {
        societeRepository.deleteById(id);
    }
}
