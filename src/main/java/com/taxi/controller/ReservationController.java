package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/Reservation") // à adapter pour chaque entité, ex: /api/voyages
public class ReservationController {

    @Autowired
    private ReservationService ReservationService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private VoyageService voyageService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private BilletService billetService;
    @Autowired
    private FactureService factureService;
     @Autowired
    private EtatPaiementService etatPaiementService;

    // Créer une entité
    @PostMapping
    public Reservation create(@RequestBody Reservation Reservation) {
        return ReservationService.create(Reservation);
    }

    // Lire toutes les entités
    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("reservations", ReservationService.getAll());
        return "Reservation/list";
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

    @GetMapping("/reserver")
    public String reserverform(Model model)
    {
        List<Utilisateur> utilisateurs = utilisateurService.getAll()
            .stream()
            .filter(utilisateur -> !"admin".equalsIgnoreCase(utilisateur.getNom()) 
                && !"agent".equalsIgnoreCase(utilisateur.getNom()))
            .collect(Collectors.toList());

        List<Voyage> voyages = voyageService.getAll();
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("voyages", voyages);
        return "Reservation/reserver";
    }

    @PostMapping("/horaire")
    public String horaire(Model model, @RequestParam("nbPlace") int nbPlace,
                          @RequestParam("nom") String nom,
                          @RequestParam("utilisateur") Long utilisateurId,
                          @RequestParam("voyage") Long voyageId,
                          HttpSession session) {
        if(nbPlace <= 0) {
            model.addAttribute("error", "Le nombre de places doit être supérieur à zéro.");
            return "Reservation/reserver";
        }
        // List<Voyage> voyages = getAllEnCours(voyageService.getById(voyageId).getTrajet().getIdTrajet());
        List<Voyage> voyages = voyageService.getAllEnCours(
            voyageService.getById(voyageId)
                .orElse(null)  // <-- Extrait le Voyage ou retourne null
                .getTrajet()
                .getIdTrajet()
        );
        session.setAttribute("nbPlace", nbPlace);
        session.setAttribute("UtilisateurId", utilisateurId);
        session.setAttribute("voyageId", voyageId);
        session.setAttribute("nom", nom);
        model.addAttribute("voyages", voyages);
        return "Reservation/horaire";
    }
    @GetMapping("/ReserverPlace") 
    public String ReserverPlace(@RequestParam("voyageId") Long voyageId,
                                HttpSession session,
                                Model model) {
        Integer nbPlace = (Integer) session.getAttribute("nbPlace");
        Utilisateur utilisateur = utilisateurService.getById(
                (Long) session.getAttribute("UtilisateurId")
            ).orElse(null);
        Voyage voyage = voyageService.getById(voyageId).orElse(null);
        String nom = (String) session.getAttribute("nom");
        Vehicule vehicule = voyageService.getById(voyageId)
            .orElse(null)
            .getVehicule();
        List<Place> placeDisponibles = placeService.getPlacesDispo(vehicule.getIdVehicule());
        if (placeDisponibles.size() < nbPlace) {
            model.addAttribute("error", "Pas assez de places disponibles.");
            return "Reservation/horaire";
        }
        Facture facture = new Facture();
        facture.genererNum();
        // facture.setNumFacture("FAC" + System.currentTimeMillis());
        facture.setUtilisateur(utilisateur);
        facture.setDate( java.time.LocalDate.now() );
        facture.setEtatPaiement( etatPaiementService.getById(2L).orElse(null) );
        facture = factureService.create(facture);
        for(int i = 0; i < nbPlace; i++) { 
            Place place = placeDisponibles.get(0); 
            placeDisponibles.remove(0);
            Billet billet = new Billet();
            billet.genererNumeroBillet();
            // billet.setNumeroBillet("BIL" + System.currentTimeMillis() + "_" + i + "_" + utilisateur.getIdUtilisateur());
            billet = billetService.create(billet);
            Reservation reservation = new Reservation();
            reservation.setUtilisateur(utilisateur);
            reservation.setVoyage(voyage);
            reservation.setPlace(place); // Place unique pour chaque réservation
            reservation.setAuNomDe(nom);
            reservation.setDateReservation(java.time.LocalDateTime.now());
            reservation.setStatut(StatusReservation.CONFIRMEE);
            reservation.setBillet(billet);
            reservation.setFacture(facture);
            reservation = ReservationService.create(reservation);
            place.setStatut(StatusPlace.RESERVEE); // Assurez-vous que cet enum existe
            placeService.update(place);
        }
        
        return "/api/Reservation/list";
    }
}
