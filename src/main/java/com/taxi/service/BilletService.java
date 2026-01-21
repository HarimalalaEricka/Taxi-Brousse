package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BilletService {

    @Autowired
    private BilletRepository BilletRepository;

    public Billet create(Billet Billet) {
        return BilletRepository.save(Billet);
    }

    public List<Billet> getAll() {
        return BilletRepository.findAll();
    }

    public Optional<Billet> getById(Long id) {
        return BilletRepository.findById(id);
    }

    public Billet update(Billet Billet) {
        return BilletRepository.save(Billet);
    }

    public void delete(Long id) {
        BilletRepository.deleteById(id);
    }

    public List<Billet> findByReservation(Reservation reservation)
    {
        return BilletRepository.findByReservation(reservation);
    }
}
