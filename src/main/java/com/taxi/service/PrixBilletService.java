package com.taxi.service;

import com.taxi.models.PrixBillet;
import com.taxi.repository.PrixBilletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrixBilletService {

    @Autowired
    private PrixBilletRepository PrixBilletRepository;

    public PrixBillet create(PrixBillet PrixBillet) {
        return PrixBilletRepository.save(PrixBillet);
    }

    public List<PrixBillet> getAll() {
        return PrixBilletRepository.findAll();
    }

    public Optional<PrixBillet> getById(Long id) {
        return PrixBilletRepository.findById(id);
    }

    public PrixBillet update(PrixBillet PrixBillet) {
        return PrixBilletRepository.save(PrixBillet);
    }

    public void delete(Long id) {
        PrixBilletRepository.deleteById(id);
    }
}
