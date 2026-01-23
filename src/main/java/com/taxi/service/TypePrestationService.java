package com.taxi.service;

import com.taxi.models.TypePrestation;
import com.taxi.repository.TypePrestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TypePrestationService {

    @Autowired
    private TypePrestationRepository typePrestationRepository;

    public TypePrestation create(TypePrestation typePrestation) {
        return typePrestationRepository.save(typePrestation);
    }

    public List<TypePrestation> getAll() {
        return typePrestationRepository.findAll();
    }

    public Optional<TypePrestation> getById(Long id) {
        return typePrestationRepository.findById(id);
    }

    public TypePrestation update(TypePrestation typePrestation) {
        return typePrestationRepository.save(typePrestation);
    }

    public void delete(Long id) {
        typePrestationRepository.deleteById(id);
    }
}
