package com.taxi.service;

import com.taxi.models.TypePaiement;
import com.taxi.repository.TypePaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypePaiementService {

    @Autowired
    private TypePaiementRepository TypePaiementRepository;

    public TypePaiement create(TypePaiement TypePaiement) {
        return TypePaiementRepository.save(TypePaiement);
    }

    public List<TypePaiement> getAll() {
        return TypePaiementRepository.findAll();
    }

    public Optional<TypePaiement> getById(Long id) {
        return TypePaiementRepository.findById(id);
    }

    public TypePaiement update(TypePaiement TypePaiement) {
        return TypePaiementRepository.save(TypePaiement);
    }

    public void delete(Long id) {
        TypePaiementRepository.deleteById(id);
    }
}
