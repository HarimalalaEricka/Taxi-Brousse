package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByFactureIdFacture(Long factureId);
    List<Reservation> findByVoyage(Voyage voyage);
    
    @Query("SELECT r FROM Reservation r JOIN r.facture f JOIN f.etatPaiement e WHERE (e.etat = 'paye' OR e.etat = 'partiellement paye') AND r.voyage = :voyage")
    List<Reservation> findByVoyageAndFacturePayee(@Param("voyage") Voyage voyage);
}
