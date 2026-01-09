package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/Voyage") // à adapter pour chaque entité, ex: /api/voyages
public class VoyageController {

    @Autowired
    private VoyageService VoyageService;
    @Autowired
    private TrajetService trajetService;
    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private ChauffeurService chauffeurService;
    @Autowired
    private EtatVoyageService etatVoyageService;

    // Créer une entité
    @PostMapping()
    public Voyage create(@RequestBody Voyage Voyage) {
        return VoyageService.create(Voyage);
    }

    // Lire toutes les entités
    @GetMapping("/list")
    public String getAll(Model model) {
        List<Voyage> voyages = VoyageService.getAll();
        model.addAttribute("voyages", voyages);
        return "Voyage/list";
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Voyage getById(@PathVariable Long id) {
        return VoyageService.getById(id).orElse(null);
    }

    // Mettre à jour un Voyage
    @PutMapping("/{id}")
    public Voyage update(@PathVariable Long id, @RequestBody Voyage updatedVoyage) {
        updatedVoyage.setIdVoyage(id);
        return VoyageService.update(updatedVoyage);
    }

    // Supprimer un Voyage
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        VoyageService.delete(id);
    }

    @GetMapping("/createVoyage")
    public String createVoyageForm(Model model) {
        List<Trajet> trajets = trajetService.getAll();
        List<Chauffeur> chauffeurs = chauffeurService.getAll();
        List<Vehicule> vehicules = vehiculeService.getAll();
        model.addAttribute("trajets", trajets);
        model.addAttribute("chauffeurs", chauffeurs);
        model.addAttribute("vehicules", vehicules);
        return "Voyage/create_voyage";
    }

    @PostMapping("/createVoyage")
    public String createVoyageSubmit(@RequestParam Long idTrajet,
                                     @RequestParam Long idVehicule,
                                     @RequestParam Long idChauffeur,
                                     @RequestParam String dateDepartStr,
                                     @RequestParam String heureDepartStr,
                                     Model model) {
        LocalDate dateDepart = LocalDate.parse(dateDepartStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime heureDepart = LocalTime.parse(heureDepartStr, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalDate aujourdhui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();
        if (dateDepart.isBefore(aujourdhui)) {
            model.addAttribute("error", "La date de départ doit être aujourd'hui ou une date future.");
            return "Voyage/create_voyage";
        }
        if (dateDepart.isEqual(aujourdhui) && heureDepart.isBefore(maintenant)) {
            model.addAttribute("error", "L'heure de départ doit être une heure future.");
            return "Voyage/create_voyage";
        }
        Voyage voyage = new Voyage();
        voyage.setTrajet(trajetService.getById(idTrajet).orElse(null));
        voyage.setVehicule(vehiculeService.getById(idVehicule).orElse(null));
        voyage.setChauffeur(chauffeurService.getById(idChauffeur).orElse(null));
        voyage.setDateDepart(dateDepart);
        voyage.setHeureDepart(heureDepart);
        EtatVoyage etatVoyage = etatVoyageService.getById(1L).orElse(null);
        voyage.setEtatVoyage(etatVoyage);
        VoyageService.create(voyage);
        List<Voyage> voyages = VoyageService.getAll();
        model.addAttribute("voyages", voyages);
        return "Voyage/list";
    }
}
