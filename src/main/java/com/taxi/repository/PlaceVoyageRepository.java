package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceVoyageRepository extends JpaRepository<PlaceVoyage, Long> {
    
    // Trouver toutes les places pour un voyage donné
    List<PlaceVoyage> findByVoyage(Voyage voyage);
    
    // Trouver toutes les places pour un voyage avec un statut donné
    List<PlaceVoyage> findByVoyageAndStatut(Voyage voyage, StatusPlace statut);
    
    // Trouver toutes les places d'un voyage par ID de voyage
    List<PlaceVoyage> findByVoyageIdVoyage(Long voyageId);
    
    // Trouver les places libres pour un voyage
    List<PlaceVoyage> findByVoyageIdVoyageAndStatut(Long voyageId, StatusPlace statut);
    
    // Trouver une PlaceVoyage par place et voyage
    Optional<PlaceVoyage> findByPlaceAndVoyage(Place place, Voyage voyage);
    
    // Trouver une PlaceVoyage par ID de place et ID de voyage
    Optional<PlaceVoyage> findByPlaceIdPlaceAndVoyageIdVoyage(Long placeId, Long voyageId);
    
    // Compter les places par statut pour un voyage
    long countByVoyageIdVoyageAndStatut(Long voyageId, StatusPlace statut);
}
