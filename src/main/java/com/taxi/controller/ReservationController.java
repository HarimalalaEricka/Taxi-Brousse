package com.taxi.controller;

import com.taxi.models.Reservation;
import com.taxi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Reservation") // à adapter pour chaque entité, ex: /api/voyages
public class ReservationController {

    @Autowired
    private ReservationService ReservationService;

    // Créer une entité
    @PostMapping
    public Reservation create(@RequestBody Reservation Reservation) {
        return ReservationService.create(Reservation);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Reservation> getAll() {
        return ReservationService.getAll();
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return ReservationService.getById(id).orElse(null);
    }

    // Mettre à jour un Reservation
    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        updatedReservation.setIdReservation(id);
        return ReservationService.update(updatedReservation);
    }

    // Supprimer un Reservation
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ReservationService.delete(id);
    }
}
