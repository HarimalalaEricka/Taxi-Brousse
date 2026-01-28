package com.taxi.service;

import com.taxi.models.*;
import com.taxi.dto.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Date;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository VoyageRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceVoyageRepository placeVoyageRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private NbrPlaceReservationRepository nbrPlaceReservationRepository;
    @Autowired
    private VuePrixBilletRepository vuePrixBilletRepository;
    @Autowired
    private PrixBilletRepository prixBilletRepository;
    @Autowired 
    private PrixBilletService prixBilletService;
    @Autowired
    private PaiementFactureSocieteService paiementFactureSocieteService;

    public Voyage create(Voyage voyage) {
        // Sauvegarder le voyage d'abord
        Voyage savedVoyage = VoyageRepository.save(voyage);
        
        // Initialiser les PlaceVoyage pour ce voyage
        if (savedVoyage.getVehicule() != null) {
            initializePlacesForVoyage(savedVoyage);
        }
        
        return savedVoyage;
    }

    /**
     * Initialise les PlaceVoyage pour un nouveau voyage
     * Copie toutes les places du véhicule et les met en statut LIBRE
     */
    private void initializePlacesForVoyage(Voyage voyage) {
        List<Place> placesVehicule = placeRepository.findByVehicule(voyage.getVehicule());
        
        for (Place place : placesVehicule) {
            PlaceVoyage pv = new PlaceVoyage();
            pv.setPlace(place);
            pv.setVoyage(voyage);
            pv.setStatut(StatusPlace.LIBRE);
            placeVoyageRepository.save(pv);
        }
    }

    public List<Voyage> getAll() {
        return VoyageRepository.findAll();
    }

    public Optional<Voyage> getById(Long id) {
        return VoyageRepository.findById(id);
    }

    public Voyage update(Voyage Voyage) {
        return VoyageRepository.save(Voyage);
    }

    public void delete(Long id) {
        VoyageRepository.deleteById(id);
    }
    public List<Voyage> getAllEnCours(Long idTrajet) {
        return VoyageRepository.findByEtatVoyage_EtatAndTrajet_IdTrajet("en attente", idTrajet);
    }
    
    public double calculValeurMax(Voyage voyage) {
        double valeurMax = 0.0;
        
        if (voyage.getVehicule() == null || voyage.getTrajet() == null) {
            return valeurMax;
        }
        
        // Utiliser LocalDate au lieu de Date
        LocalDate aujourdhui = LocalDate.now();
        
        // Récupérer les places du véhicule
        List<Place> places = placeRepository.findByVehicule(voyage.getVehicule());
        
        // Récupérer les prix actifs pour ce trajet
        List<PrixBillet> prixBillets = prixBilletRepository.findByTrajetAndDateFinIsNull(voyage.getTrajet());
        
        // Filtrer pour garder seulement les prix avec date début <= aujourd'hui
        Map<TypePlace, Double> prixParType = prixBillets.stream()
            .filter(prix -> !prix.getDateDebut().isAfter(aujourdhui))  // dateDebut <= aujourdhui
            .collect(Collectors.toMap(
                PrixBillet::getTypePlace,
                prix -> prix.getPrix().doubleValue()  // Conversion BigDecimal -> double
            ));
        
        // Calculer la valeur maximale
        for (Place place : places) {
            TypePlace typePlace = place.getTypePlace();
            if (prixParType.containsKey(typePlace)) {
                    System.out.println("===========================================");
                    System.out.println("VALEUR MAX" + voyage.getTrajet().getIdTrajet());
                    System.out.println(valeurMax + " += " + prixParType.get(typePlace) );
                    System.out.println("===========================================");
                valeurMax += prixParType.get(typePlace);
            }
        }
        
        return valeurMax;
    }
    public double calculChiffreAffaire(Voyage voyage) 
    {
        if (voyage == null || voyage.getTrajet() == null) {
            return 0.0;
        }
        
        Long idTrajet = voyage.getTrajet().getIdTrajet();
        double chiffreAffaireTotal = 0.0;
        
        // Récupérer toutes les réservations pour ce voyage
        // Si reservation non payes mais estimation
        List<Reservation> reservations = reservationRepository.findByVoyage(voyage);

        // si reservation payes
        // List<Reservation> reservations = reservationRepository.findByVoyageAndFacturePayee(voyage);
        
        for (Reservation reservation : reservations) {
            // Récupérer les nbr_place_reservation pour cette réservation
            List<NbrPlaceReservation> nbrPlaces = 
                nbrPlaceReservationRepository.findByReservation(reservation);
            
            for (NbrPlaceReservation npr : nbrPlaces) {
                CategoriePersonne categoriePersonne = npr.getCategoriePersonne();
                String categorie = categoriePersonne.getCategorie();
                Long idTypePlace = categoriePersonne.getTypePlace().getIdTypePlace();
                Integer nbrPlace = npr.getNbrPlace();
                
                // Récupérer le prix final depuis la vue
                Double prixFinal = vuePrixBilletRepository.findPrixFinal(
                    idTrajet, categorie, idTypePlace
                );
                
                if (prixFinal != null) {
                    System.out.println("===========================================");
                    System.out.println("CA" + voyage.getTrajet().getIdTrajet());
                    System.out.println(chiffreAffaireTotal + " += " + prixFinal + " * " + nbrPlace);
                    System.out.println("===========================================");
                    chiffreAffaireTotal += prixFinal * nbrPlace;
                }
            }
        }
        
        return chiffreAffaireTotal;
    }

    public double calculCAPrestations(Voyage voyage) {
        double total = 0.0;
        if (voyage.getPrestations() != null) {
            for (Prestation p : voyage.getPrestations()) {
                if (p.getTarifPrestation() != null) {
                    double montant = p.getTarifPrestation().getPrixUnitaire().doubleValue() * p.getQuantite();
                    total += montant;
                }
            }
        }
        return total;
    }

    /**
     * Calcule le montant total des prestations payées pour un voyage
     */
    public double calculPrestationsPayees(Voyage voyage) {
        double totalPaye = 0.0;
        if (voyage.getPrestations() != null) {
            for (Prestation p : voyage.getPrestations()) {
                if (p.getFactureSociete() != null) {
                    // Utiliser le nouveau système de paiement par facture société
                    BigDecimal montantPaye = paiementFactureSocieteService.getMontantPayePourPrestation(p);
                    totalPaye += montantPaye.doubleValue();
                }
            }
        }
        return totalPaye;
    }

    /**
     * Calcule le reste à payer pour les prestations d'un voyage
     */
    public double calculPrestationsResteAPayer(Voyage voyage) {
        double totalReste = 0.0;
        if (voyage.getPrestations() != null) {
            for (Prestation p : voyage.getPrestations()) {
                if (p.getFactureSociete() != null) {
                    // Utiliser le nouveau système de paiement par facture société
                    BigDecimal montantPaye = paiementFactureSocieteService.getMontantPayePourPrestation(p);
                    BigDecimal montantTotal = p.getMontantTotal();
                    BigDecimal reste = montantTotal.subtract(montantPaye);
                    totalReste += reste.doubleValue();
                } else {
                    // Prestation non facturée = reste à payer = montant total
                    totalReste += p.getMontantTotal().doubleValue();
                }
            }
        }
        return totalReste;
    }

    public List<VoyageValeur> getVoyagesWithValeurMax( List<Voyage> voyages)
    {
        List<VoyageValeur> result = new ArrayList<>();
        for( Voyage v : voyages)
        {
            double valeurMax = calculValeurMax(v);
            double chiffreAffaire = calculChiffreAffaire(v);
            double caPrestation = calculCAPrestations(v);
            double prestationsPayees = calculPrestationsPayees(v);
            double prestationsResteAPayer = calculPrestationsResteAPayer(v);
            result.add( new VoyageValeur(v, valeurMax, chiffreAffaire, caPrestation, prestationsPayees, prestationsResteAPayer) );
        }
        return result;
    }

    public List<Voyage> getByMoisAnnee(int mois, int annee) {
        return VoyageRepository.findByMoisAndAnnee(mois, annee);
    }

    public double calculerCAVoyages(int mois, int annee) {
        List<Voyage> voyages = VoyageRepository.findByMoisAndAnnee(mois, annee);
        double total = 0.0;
        for (Voyage v : voyages) {
            total += calculChiffreAffaire(v);
        }
        return total;
    }
}
