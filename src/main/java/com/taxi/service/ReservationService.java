package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository ReservationRepository;
    @Autowired
    private NbrPlaceReservationRepository nbrPlaceReservationRepository;
    @Autowired
    private VuePrixBilletRepository vuePrixBilletRepository;
    @Autowired
    private FactureService factureService;

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
    public Optional<Reservation> getByFactureId(Long factureId) {
        return ReservationRepository.findByFactureIdFacture(factureId)
                .stream()
                .findFirst();
    }

    public List<Map<String, Object>> getLignesPrixReservation(Reservation reservation) {
    List<Map<String, Object>> lignes = new ArrayList<>();
    
    if (reservation == null || reservation.getVoyage() == null) {
        return lignes;
    }
    
    Long idTrajet = reservation.getVoyage().getTrajet().getIdTrajet();
    List<NbrPlaceReservation> nbrPlaces = 
        nbrPlaceReservationRepository.findByReservation(reservation);
    
    for (NbrPlaceReservation npr : nbrPlaces) {
        Map<String, Object> ligne = new HashMap<>();
        
        CategoriePersonne cp = npr.getCategoriePersonne();
        TypePlace tp = cp.getTypePlace();
        
        Double prixFinal = vuePrixBilletRepository.findPrixFinal(
            idTrajet, cp.getCategorie(), tp.getIdTypePlace()
        );
        
        if (prixFinal != null) {
            ligne.put("categoriePersonne", cp.getCategorie());
            ligne.put("typePlace", tp.getType());
            ligne.put("nbrPlace", npr.getNbrPlace());
            ligne.put("prixFinal", prixFinal);
            ligne.put("sousTotal", prixFinal * npr.getNbrPlace());
            
            lignes.add(ligne);
        }
    }
    
    return lignes;
}
public Map<String, Object> calculDetailsPrixParReservation(Reservation reservation) {
    Map<String, Object> details = new HashMap<>();
    
    if (reservation == null || reservation.getVoyage() == null || 
        reservation.getVoyage().getTrajet() == null) {
        return details;
    }
    
    // Informations de base
    Long idReservation = reservation.getIdReservation();
    Long idTrajet = reservation.getVoyage().getTrajet().getIdTrajet();
    String nomPassager = reservation.getAuNomDe();
    
    // Initialiser les listes pour stocker les détails
    List<Map<String, Object>> lignesDetails = new ArrayList<>();
    double totalReservation = 0.0;
    
    // Récupérer les nbr_place_reservation pour cette réservation
    List<NbrPlaceReservation> nbrPlaces = 
        nbrPlaceReservationRepository.findByReservation(reservation);
    
    for (NbrPlaceReservation npr : nbrPlaces) {
        Map<String, Object> ligne = new HashMap<>();
        
        CategoriePersonne categoriePersonne = npr.getCategoriePersonne();
        TypePlace typePlace = categoriePersonne.getTypePlace();
        
        // Récupérer les informations
        String categorie = categoriePersonne.getCategorie();
        String typePlaceNom = typePlace.getType();
        Integer nbrPlace = npr.getNbrPlace();
        
        // Récupérer le prix final depuis la vue
        Double prixFinal = vuePrixBilletRepository.findPrixFinal(
            idTrajet, categorie, typePlace.getIdTypePlace()
        );
        
        if (prixFinal != null) {
            double sousTotal = prixFinal * nbrPlace;
            totalReservation += sousTotal;
            
            // Remplir la ligne de détail
            ligne.put("categoriePersonne", categorie);
            ligne.put("typePlace", typePlaceNom);
            ligne.put("nbrPlace", nbrPlace);
            ligne.put("prixUnitaire", prixFinal);
            ligne.put("prixFinal", prixFinal); // prix unitaire après réduction
            ligne.put("sousTotal", sousTotal);
            ligne.put("reductionPourcentage", categoriePersonne.getReductionPourcentage());
            ligne.put("reductionFixe", categoriePersonne.getReductionFixe());
            
            lignesDetails.add(ligne);
        }
    }
    
    details.put("idReservation", idReservation);
    details.put("idTrajet", idTrajet);
    details.put("nomPassager", nomPassager);
    details.put("detailsLignes", lignesDetails);
    details.put("totalReservation", totalReservation);
    details.put("nombreTotalPlaces", lignesDetails.stream()
        .mapToInt(l -> (Integer) l.get("nbrPlace"))
        .sum());

    Facture facture = reservation.getFacture();
    if (facture != null && facture.getMontant() == null) {
        facture.setMontant(java.math.BigDecimal.valueOf(totalReservation));
        factureService.update(facture);
    }
    
    return details;
}
}
