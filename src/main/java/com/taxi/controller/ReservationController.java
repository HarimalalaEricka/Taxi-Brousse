package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/api/Reservation")
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
    @Autowired
    private PrixBilletService prixBilletService;
    @Autowired
    private CategoriePersonneService categoriePersonneService;
    @Autowired
    private TypePlaceService typePlaceService;
    @Autowired
    private NbrPlaceReservationService nbrPlaceReservationService;

    @PostMapping
    public Reservation create(@RequestBody Reservation Reservation) {
        return ReservationService.create(Reservation);
    }

    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("reservations", ReservationService.getAll());
        model.addAttribute("title", "Réservations");
        model.addAttribute("content", "Reservation/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }


    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return ReservationService.getById(id).orElse(null);
    }
    @GetMapping("/{id}/billets")
    public String listBillets( @PathVariable Long id, Model model)
    {
        Reservation reservation = ReservationService.getById(id).get();
        List<Billet> billets = billetService.findByReservation(reservation);
        model.addAttribute("billets", billets);
        model.addAttribute("title", "Billet");
        model.addAttribute("content", "Billet/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        updatedReservation.setIdReservation(id);
        return ReservationService.update(updatedReservation);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ReservationService.delete(id);
    }

    @GetMapping("/reserver")
    public String reserverform(Model model) {
        System.out.println("=== DÉBUT GET /reserver ===");
        
        List<CategoriePersonne> allCategories = categoriePersonneService.getAll();
        System.out.println("Nombre total de catégories trouvées: " + allCategories.size());
        
        Map<String, List<CategoriePersonne>> categoriesByType = allCategories.stream()
            .collect(Collectors.groupingBy(CategoriePersonne::getCategorie));
        System.out.println("Catégories groupées: " + categoriesByType.keySet());
        
        List<TypePlace> typesPlace = typePlaceService.getAll();
        System.out.println("Types de place trouvés: " + typesPlace.size());
        
        List<Utilisateur> utilisateurs = utilisateurService.getAll()
            .stream()
            .filter(utilisateur -> !"admin".equalsIgnoreCase(utilisateur.getNom()) 
                && !"agent".equalsIgnoreCase(utilisateur.getNom()))
            .collect(Collectors.toList());
        System.out.println("Utilisateurs filtrés: " + utilisateurs.size());
        
        List<Voyage> voyages = voyageService.getAll();
        System.out.println("Voyages trouvés: " + voyages.size());
        
        model.addAttribute("categoriesByType", categoriesByType);
        model.addAttribute("typesPlace", typesPlace);
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("voyages", voyages);
        model.addAttribute("title", "Réservations");
        model.addAttribute("content", "Reservation/reserver");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";

    }

    @PostMapping("/horaire")
    public String horaire(
            @RequestParam("nom") String nom,
            @RequestParam("utilisateur") Long utilisateurId,
            @RequestParam("voyage") Long voyageId,
            @RequestParam Map<String, String> allParams,
            HttpSession session,
            Model model) {
        
        System.out.println("=== DÉBUT POST /horaire ===");
        System.out.println("Paramètres de base reçus:");
        System.out.println("  nom: " + nom);
        System.out.println("  utilisateur: " + utilisateurId);
        System.out.println("  voyage: " + voyageId);
        
        try {
            // 1. Validation du nom
            if (nom == null || nom.trim().isEmpty()) {
                System.out.println("ERREUR: Nom vide");
                model.addAttribute("error", "Le nom pour la réservation est obligatoire.");
                return "Reservation/reserver";
            }
            
            // 2. Afficher tous les paramètres pour débogage
            System.out.println("Tous les paramètres reçus (" + allParams.size() + "):");
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                System.out.println("  " + entry.getKey() + " = '" + entry.getValue() + "'");
            }
            
            // 3. Calculer le nombre total de places et stocker le détail
            int totalPlaces = 0;
            Map<Long, Integer> placesParCategorie = new HashMap<>();
            
            System.out.println("Recherche des paramètres nbrPlaces[...]:");
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("nbrPlaces[")) {
                    System.out.println("  Trouvé: " + key + " = '" + entry.getValue() + "'");
                    
                    try {
                        // Extraction de l'ID: nbrPlaces[1] -> 1
                        String idStr = key.substring("nbrPlaces[".length(), key.length() - 1);
                        Long categoriePersonneId = Long.parseLong(idStr);
                        
                        int quantite = 0;
                        String valeur = entry.getValue();
                        if (valeur != null && !valeur.trim().isEmpty()) {
                            quantite = Integer.parseInt(valeur.trim());
                        }
                        
                        System.out.println("    ID Catégorie: " + categoriePersonneId + ", Quantité: " + quantite);
                        
                        if (quantite > 0) {
                            totalPlaces += quantite;
                            placesParCategorie.put(categoriePersonneId, quantite);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("    ERREUR parsing pour " + key + ": " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("    ERREUR générale pour " + key + ": " + e.getMessage());
                    }
                }
            }
            
            System.out.println("Résultat du calcul:");
            System.out.println("  Total places: " + totalPlaces);
            System.out.println("  Places par catégorie: " + placesParCategorie);
            
            // 4. Vérifier qu'au moins une place est sélectionnée
            if (totalPlaces <= 0) {
                System.out.println("ERREUR: Aucune place sélectionnée");
                model.addAttribute("error", "Veuillez sélectionner au moins une place.");
                return "Reservation/reserver";
            }
            
            // 5. Stocker en session
            session.setAttribute("nbPlace", totalPlaces);
            session.setAttribute("UtilisateurId", utilisateurId);
            session.setAttribute("voyageId", voyageId);
            session.setAttribute("nom", nom);
            session.setAttribute("placesParCategorie", placesParCategorie);
            
            System.out.println("Session mise à jour:");
            System.out.println("  nbPlace: " + totalPlaces);
            System.out.println("  UtilisateurId: " + utilisateurId);
            System.out.println("  voyageId: " + voyageId);
            System.out.println("  nom: " + nom);
            System.out.println("  placesParCategorie: " + placesParCategorie);
            
            // 6. Récupérer les voyages en cours pour ce trajet
            System.out.println("Recherche du voyage ID: " + voyageId);
            Voyage voyage = voyageService.getById(voyageId).orElse(null);
            if (voyage == null) {
                System.out.println("ERREUR: Voyage non trouvé");
                model.addAttribute("error", "Voyage non trouvé.");
                return "Reservation/reserver";
            }
            System.out.println("Voyage trouvé: " + voyage.getIdVoyage());
            
            System.out.println("Recherche des voyages en cours pour trajet ID: " + voyage.getTrajet().getIdTrajet());
            List<Voyage> voyages = voyageService.getAllEnCours(voyage.getTrajet().getIdTrajet());
            System.out.println("Voyages en cours trouvés: " + voyages.size());
            
            model.addAttribute("voyages", voyages);
            
            System.out.println("=== FIN POST /horaire - Redirection vers horaire.html ===");
            return "Reservation/horaire";
            
        } catch (Exception e) {
            System.err.println("=== ERREUR dans POST /horaire ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=== FIN ERREUR ===");
            
            model.addAttribute("error", "Erreur: " + e.getMessage());
            return "Reservation/reserver";
        }
    }

    @GetMapping("/ReserverPlace")
    @Transactional
    public String ReserverPlace(@RequestParam("voyageId") Long voyageId,
                                HttpSession session,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        System.out.println("=== DÉBUT GET /ReserverPlace ===");
        System.out.println("Paramètre voyageId: " + voyageId);
        
        try {
            // 1. Afficher le contenu complet de la session
            System.out.println("Contenu de la session:");
            java.util.Enumeration<String> sessionAttrs = session.getAttributeNames();
            boolean hasAttributes = false;
            while (sessionAttrs.hasMoreElements()) {
                hasAttributes = true;
                String attrName = sessionAttrs.nextElement();
                Object attrValue = session.getAttribute(attrName);
                System.out.println("  " + attrName + " = " + attrValue + 
                                 " (type: " + (attrValue != null ? attrValue.getClass().getSimpleName() : "null") + ")");
            }
            if (!hasAttributes) {
                System.out.println("  Session vide");
            }
            
            // 2. Validation des données de session
            Integer nbPlace = (Integer) session.getAttribute("nbPlace");
            Long utilisateurId = (Long) session.getAttribute("UtilisateurId");
            String nom = (String) session.getAttribute("nom");
            Long sessionVoyageId = (Long) session.getAttribute("voyageId");
            
            @SuppressWarnings("unchecked")
            Map<Long, Integer> placesParCategorie = (Map<Long, Integer>) session.getAttribute("placesParCategorie");
            
            System.out.println("Données extraites de session:");
            System.out.println("  nbPlace: " + nbPlace);
            System.out.println("  utilisateurId: " + utilisateurId);
            System.out.println("  nom: " + nom);
            System.out.println("  sessionVoyageId: " + sessionVoyageId);
            System.out.println("  placesParCategorie: " + (placesParCategorie != null ? placesParCategorie : "null"));
            
            if (nbPlace == null || utilisateurId == null || nom == null || placesParCategorie == null) {
                System.out.println("ERREUR: Données de session manquantes");
                redirectAttributes.addFlashAttribute("error", "Données de session manquantes. Veuillez recommencer.");
                return "redirect:/api/Reservation/reserver";
            }
            
            // Vérifier la cohérence du voyageId
            if (sessionVoyageId == null || !sessionVoyageId.equals(voyageId)) {
                System.out.println("ERREUR: Incohérence voyageId - Session: " + sessionVoyageId + ", Paramètre: " + voyageId);
                redirectAttributes.addFlashAttribute("error", "Incohérence dans les données de voyage.");
                return "redirect:/api/Reservation/reserver";
            }
            
            // 3. Récupération et validation des entités
            System.out.println("Récupération utilisateur ID: " + utilisateurId);
            Utilisateur utilisateur = utilisateurService.getById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID: " + utilisateurId));
            System.out.println("Utilisateur trouvé: ID=" + utilisateur.getIdUtilisateur() + 
                             ", Nom=" + utilisateur.getNom() + " " + utilisateur.getPrenom());
            
            System.out.println("Récupération voyage ID: " + voyageId);
            Voyage voyage = voyageService.getById(voyageId)
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé avec ID: " + voyageId));
            System.out.println("Voyage trouvé: ID=" + voyage.getIdVoyage() + 
                             ", Trajet=" + voyage.getTrajet().getVilleDepart() + "→" + voyage.getTrajet().getVilleArrivee());
            
            Vehicule vehicule = voyage.getVehicule();
            if (vehicule == null) {
                System.out.println("ERREUR: Aucun véhicule associé au voyage");
                redirectAttributes.addFlashAttribute("error", "Aucun véhicule associé à ce voyage.");
                return "redirect:/api/Reservation/reserver";
            }
            System.out.println("Véhicule trouvé: ID=" + vehicule.getIdVehicule() + 
                             ", Immatriculation=" + vehicule.getImmatriculation() + 
                             ", Places totales=" + vehicule.getNombrePlaces());
            
            // 4. Vérification des places disponibles
            System.out.println("Recherche places disponibles pour véhicule ID: " + vehicule.getIdVehicule());
            List<Place> placesDisponibles = placeService.getPlacesDispo(vehicule.getIdVehicule());
            System.out.println("Places disponibles: " + placesDisponibles.size());
            
            // Afficher les places disponibles
            for (Place place : placesDisponibles) {
                System.out.println("  Place ID=" + place.getIdPlace() + 
                                 ", Numéro=" + place.getNumeroPlace() + 
                                 ", Type=" + (place.getTypePlace() != null ? place.getTypePlace().getType() : "null"));
            }
            
            if (placesDisponibles.size() < nbPlace) {
                System.out.println("ERREUR: Places insuffisantes. Disponibles: " + placesDisponibles.size() + ", Demandées: " + nbPlace);
                redirectAttributes.addFlashAttribute("error", 
                    "Pas assez de places disponibles. Il reste seulement " + 
                    placesDisponibles.size() + " place(s).");
                return "redirect:/api/Reservation/reserver";
            }
            
            // 5. Création de la facture
            System.out.println("Création de la facture...");
            Facture facture = new Facture();
            facture.genererNum();
            facture.setUtilisateur(utilisateur);
            facture.setDate(java.time.LocalDate.now());
            
            System.out.println("Recherche état paiement ID: 2");
            EtatPaiement etatPaiement = etatPaiementService.getById(2L)
                .orElseThrow(() -> new RuntimeException("État de paiement non trouvé avec ID: 2"));
            facture.setEtatPaiement(etatPaiement);
            
            System.out.println("Appel factureService.create()");
            facture = factureService.create(facture);
            System.out.println("Facture créée avec ID: " + facture.getIdFacture() + 
                             ", Numéro: " + facture.getNumFacture());
            
            // 6. Création de la réservation
            System.out.println("Création de la réservation...");
            Reservation reservation = new Reservation();
            reservation.setUtilisateur(utilisateur);
            reservation.setVoyage(voyage);
            reservation.setAuNomDe(nom);
            reservation.setDateReservation(java.time.LocalDateTime.now());
            reservation.setStatut(StatusReservation.CONFIRMEE);
            reservation.setFacture(facture);
            
            System.out.println("Appel ReservationService.create()");
            reservation = ReservationService.create(reservation);
            System.out.println("Réservation créée avec ID: " + reservation.getIdReservation() + 
                             ", Nom: " + reservation.getAuNomDe());
            
            // 7. Création des nbr_place_reservation
            System.out.println("Création des nbr_place_reservation...");
            System.out.println("Nombre d'entrées à créer: " + placesParCategorie.size());
            
            for (Map.Entry<Long, Integer> entry : placesParCategorie.entrySet()) {
                Long categoriePersonneId = entry.getKey();
                Integer nbrPlace = entry.getValue();
                
                System.out.println("  Traitement catégorie ID: " + categoriePersonneId + ", nombre: " + nbrPlace);
                
                if (nbrPlace > 0) {
                    System.out.println("  Recherche catégorie personne ID: " + categoriePersonneId);
                    CategoriePersonne cp = categoriePersonneService.getById(categoriePersonneId)
                        .orElseThrow(() -> new RuntimeException(
                            "Catégorie personne non trouvée avec ID: " + categoriePersonneId));
                    
                    System.out.println("  Catégorie trouvée: " + cp.getCategorie() + 
                                     ", Type place: " + (cp.getTypePlace() != null ? cp.getTypePlace().getType() : "null"));
                    
                    NbrPlaceReservation npr = new NbrPlaceReservation();
                    npr.setReservation(reservation);
                    npr.setCategoriePersonne(cp);
                    npr.setNbrPlace(nbrPlace);
                    
                    System.out.println("  Appel nbrPlaceReservationService.create()");
                    NbrPlaceReservation savedNpr = nbrPlaceReservationService.create(npr);
                    System.out.println("  NbrPlaceReservation créé avec ID: " + savedNpr.getIdNbrPlaceReservation());
                } else {
                    System.out.println("  Skipped (nbrPlace = 0)");
                }
            }
            
            // 8. Création des billets et mise à jour des places
            System.out.println("Création des billets (" + nbPlace + " billets à créer)...");
            
            // Grouper les places disponibles par type
            Map<Long, List<Place>> placesParType = placesDisponibles.stream()
                .collect(Collectors.groupingBy(p -> p.getTypePlace().getIdTypePlace()));
            
            System.out.println("Places groupées par type:");
            for (Map.Entry<Long, List<Place>> entry : placesParType.entrySet()) {
                System.out.println("  Type ID " + entry.getKey() + ": " + entry.getValue().size() + " places");
            }
            
            int billetsCrees = 0;
            
            // Pour chaque catégorie, attribuer les places correspondantes
            for (Map.Entry<Long, Integer> entry : placesParCategorie.entrySet()) {
                Long categoriePersonneId = entry.getKey();
                Integer nbrPlace = entry.getValue();
                
                if (nbrPlace > 0) {
                    System.out.println("Attribution pour catégorie ID " + categoriePersonneId + " (" + nbrPlace + " places):");
                    
                    CategoriePersonne cp = categoriePersonneService.getById(categoriePersonneId)
                        .orElseThrow(() -> new RuntimeException(
                            "Catégorie personne non trouvée avec ID: " + categoriePersonneId));
                    
                    Long typePlaceId = cp.getTypePlace().getIdTypePlace();
                    System.out.println("  Type de place requis: ID=" + typePlaceId);
                    
                    List<Place> placesDeCeType = placesParType.getOrDefault(typePlaceId, new ArrayList<>());
                    System.out.println("  Places disponibles de ce type: " + placesDeCeType.size());
                    
                    int placesAAttribuer = Math.min(nbrPlace, placesDeCeType.size());
                    System.out.println("  Places à attribuer: " + placesAAttribuer);
                    
                    for (int i = 0; i < placesAAttribuer; i++) {
                        Place place = placesDeCeType.get(i);
                        System.out.println("    Attribution place ID: " + place.getIdPlace() + 
                                         ", Numéro: " + place.getNumeroPlace());
                        
                        // Créer le billet
                        Billet billet = new Billet();
                        billet.genererNumeroBillet();
                        billet.setReservation(reservation);
                        billet.setPlace(place);
                        
                        System.out.println("    Appel billetService.create()");
                        billet = billetService.create(billet);
                        System.out.println("    Billet créé avec ID: " + billet.getIdBillet() + 
                                         ", Numéro: " + billet.getNumeroBillet());
                        
                        // Mettre à jour le statut de la place
                        System.out.println("    Mise à jour statut place ID: " + place.getIdPlace() + " -> RESERVEE");
                        place.setStatut(StatusPlace.RESERVEE);
                        
                        System.out.println("    Appel placeService.update()");
                        placeService.update(place);
                        
                        billetsCrees++;
                        System.out.println("    Billets créés total: " + billetsCrees);
                    }
                    
                    // Retirer les places utilisées
                    if (placesDeCeType.size() > nbrPlace) {
                        System.out.println("    Suppression des " + nbrPlace + " premières places utilisées");
                        placesDeCeType.subList(0, nbrPlace).clear();
                    } else {
                        System.out.println("    Suppression du type " + typePlaceId + " de la map");
                        placesParType.remove(typePlaceId);
                    }
                }
            }
            
            // Vérification finale
            System.out.println("Vérification finale:");
            System.out.println("  Billets créés: " + billetsCrees);
            System.out.println("  Places demandées: " + nbPlace);
            
            if (billetsCrees != nbPlace) {
                throw new RuntimeException("Nombre de billets créés (" + billetsCrees + 
                                        ") ne correspond pas au nombre de places demandées (" + nbPlace + ")");
            }
            
            // 9. Nettoyage de la session
            System.out.println("Nettoyage de la session...");
            session.removeAttribute("nbPlace");
            session.removeAttribute("nom");
            session.removeAttribute("placesParCategorie");
            
            // 10. Message de succès
            redirectAttributes.addFlashAttribute("success", 
                "Réservation confirmée pour " + nbPlace + " place(s) !");
            
            System.out.println("=== FIN GET /ReserverPlace - SUCCÈS ===");
            System.out.println("Redirection vers: /api/Facture/" + facture.getIdFacture());
            return "redirect:/api/Facture/" + facture.getIdFacture();
            
        } catch (Exception e) {
            System.err.println("=== ERREUR dans GET /ReserverPlace ===");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=== FIN ERREUR ===");
            
            redirectAttributes.addFlashAttribute("error", 
                "Une erreur est survenue lors de la réservation: " + e.getMessage());
            return "redirect:/api/Reservation/reserver";
        }
    }

    
}