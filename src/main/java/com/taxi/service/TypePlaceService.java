package com.taxi.service;

import com.taxi.models.TypePlace;
import com.taxi.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypePlaceService {

    @Autowired
    private TypePlaceRepository TypePlaceRepository;

    public TypePlace create(TypePlace TypePlace) {
        return TypePlaceRepository.save(TypePlace);
    }

    public List<TypePlace> getAll() {
        return TypePlaceRepository.findAll();
    }

    public Optional<TypePlace> getById(Long id) {
        return TypePlaceRepository.findById(id);
    }

    public TypePlace update(TypePlace TypePlace) {
        return TypePlaceRepository.save(TypePlace);
    }

    public void delete(Long id) {
        TypePlaceRepository.deleteById(id);
    }
}
