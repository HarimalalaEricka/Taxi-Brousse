package com.taxi.service;

import com.taxi.models.CategoriePersonne;
import com.taxi.repository.CategoriePersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriePersonneService {

    @Autowired
    private CategoriePersonneRepository categoriePersonneRepository;

    public CategoriePersonne create(CategoriePersonne categoriePersonne) {
        return categoriePersonneRepository.save(categoriePersonne);
    }

    public List<CategoriePersonne> getAll() {
        return categoriePersonneRepository.findAll();
    }

    public Optional<CategoriePersonne> getById(Long id) {
        return categoriePersonneRepository.findById(id);
    }

    public CategoriePersonne update(CategoriePersonne categoriePersonne) {
        return categoriePersonneRepository.save(categoriePersonne);
    }

    public void delete(Long id) {
        categoriePersonneRepository.deleteById(id);
    }
}
