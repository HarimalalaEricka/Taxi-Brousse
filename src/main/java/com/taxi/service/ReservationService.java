package com.taxi.service;

import com.taxi.models.Reservation;
import com.taxi.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository ReservationRepository;

    public Reservation create(Reservation Reservation) {
        return ReservationRepository.save(Reservation);
    }

    public List<Reservation> getAll() {
        return ReservationRepository.findAll();
    }

    public Optional<Reservation> getById(Long id) {
        return ReservationRepository.findById(id);
    }

    public Reservation update(Reservation Reservation) {
        return ReservationRepository.save(Reservation);
    }

    public void delete(Long id) {
        ReservationRepository.deleteById(id);
    }
}
