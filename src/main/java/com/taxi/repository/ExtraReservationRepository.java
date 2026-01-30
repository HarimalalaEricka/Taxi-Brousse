package com.taxi.repository;

import com.taxi.models.ExtraReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraReservationRepository extends JpaRepository<ExtraReservation, Long> {
}
