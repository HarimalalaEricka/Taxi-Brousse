package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NbrPlaceReservationRepository extends JpaRepository<NbrPlaceReservation, Long> {
    List<NbrPlaceReservation> findByReservation(Reservation reservation);
}
