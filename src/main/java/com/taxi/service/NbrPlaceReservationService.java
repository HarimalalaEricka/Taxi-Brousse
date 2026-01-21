package com.taxi.service;

import com.taxi.models.NbrPlaceReservation;
import com.taxi.repository.NbrPlaceReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NbrPlaceReservationService {

    @Autowired
    private NbrPlaceReservationRepository NbrPlaceReservationRepository;

    public NbrPlaceReservation create(NbrPlaceReservation NbrPlaceReservation) {
        return NbrPlaceReservationRepository.save(NbrPlaceReservation);
    }

    public List<NbrPlaceReservation> getAll() {
        return NbrPlaceReservationRepository.findAll();
    }

    public Optional<NbrPlaceReservation> getById(Long id) {
        return NbrPlaceReservationRepository.findById(id);
    }

    public NbrPlaceReservation update(NbrPlaceReservation NbrPlaceReservation) {
        return NbrPlaceReservationRepository.save(NbrPlaceReservation);
    }

    public void delete(Long id) {
        NbrPlaceReservationRepository.deleteById(id);
    }

    
}
