package com.taxi.service;

import com.taxi.models.Voyage;
import com.taxi.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository VoyageRepository;

    public Voyage create(Voyage Voyage) {
        return VoyageRepository.save(Voyage);
    }

    public List<Voyage> getAll() {
        return VoyageRepository.findAll();
    }

    public Optional<Voyage> getById(Long id) {
        return VoyageRepository.findById(id);
    }

    public Voyage update(Voyage Voyage) {
        return VoyageRepository.save(Voyage);
    }

    public void delete(Long id) {
        VoyageRepository.deleteById(id);
    }
    public List<Voyage> getAllEnCours(Long idTrajet) {
        return VoyageRepository.findByEtatVoyage_EtatAndTrajet_IdTrajet("en attente", idTrajet);
    }
}
