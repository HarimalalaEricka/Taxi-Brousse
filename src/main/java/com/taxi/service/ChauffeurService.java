package com.taxi.service;

import com.taxi.models.Chauffeur;
import com.taxi.repository.ChauffeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChauffeurService {

    @Autowired
    private ChauffeurRepository ChauffeurRepository;

    public Chauffeur create(Chauffeur Chauffeur) {
        return ChauffeurRepository.save(Chauffeur);
    }

    public List<Chauffeur> getAll() {
        return ChauffeurRepository.findAll();
    }

    public Optional<Chauffeur> getById(Long id) {
        return ChauffeurRepository.findById(id);
    }

    public Chauffeur update(Chauffeur Chauffeur) {
        return ChauffeurRepository.save(Chauffeur);
    }

    public void delete(Long id) {
        ChauffeurRepository.deleteById(id);
    }
}
