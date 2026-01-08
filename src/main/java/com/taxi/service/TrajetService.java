package com.taxi.service;

import com.taxi.models.Trajet;
import com.taxi.repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository TrajetRepository;

    public Trajet create(Trajet Trajet) {
        return TrajetRepository.save(Trajet);
    }

    public List<Trajet> getAll() {
        return TrajetRepository.findAll();
    }

    public Optional<Trajet> getById(Long id) {
        return TrajetRepository.findById(id);
    }

    public Trajet update(Trajet Trajet) {
        return TrajetRepository.save(Trajet);
    }

    public void delete(Long id) {
        TrajetRepository.deleteById(id);
    }
}
