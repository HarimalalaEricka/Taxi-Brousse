package com.taxi.service;

import com.taxi.models.TarifPrestation;
import com.taxi.models.TypePrestation;
import com.taxi.repository.TarifPrestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TarifPrestationService {

    @Autowired
    private TarifPrestationRepository tarifPrestationRepository;

    public TarifPrestation create(TarifPrestation tarifPrestation) {
        return tarifPrestationRepository.save(tarifPrestation);
    }

    public List<TarifPrestation> getAll() {
        return tarifPrestationRepository.findAll();
    }

    public Optional<TarifPrestation> getById(Long id) {
        return tarifPrestationRepository.findById(id);
    }

    public TarifPrestation update(TarifPrestation tarifPrestation) {
        return tarifPrestationRepository.save(tarifPrestation);
    }

    public void delete(Long id) {
        tarifPrestationRepository.deleteById(id);
    }

    public List<TarifPrestation> getTarifActif(TypePrestation typePrestation) {
        return tarifPrestationRepository.findByTypePrestationAndDateFinIsNull(typePrestation);
    }
}
