package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByFactureIdFacture(Long factureId);
    List<Reservation> findByVoyage(Voyage voyage);
}
