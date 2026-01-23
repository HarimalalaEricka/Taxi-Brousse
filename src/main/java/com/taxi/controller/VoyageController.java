package com.taxi.controller;

import com.taxi.dto.*;
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
        List<VoyageValeur> valeursMax = VoyageService.getVoyagesWithValeurMax(voyages);
        model.addAttribute("valeursMax", valeursMax);
        model.addAttribute("title", "Voyage");
        model.addAttribute("content", "Voyage/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
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
        model.addAttribute("title", "Voyage");
        model.addAttribute("content", "Voyage/create_voyage");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
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
        return "redirect:/api/Voyage/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoyage(@PathVariable Long id) {
        VoyageService.delete(id);
        return "redirect:/api/Voyage/list";
    }

    @GetMapping("/annuler/{id}")
    public String annulerVoyage(@PathVariable Long id) {
        VoyageService.getById(id).ifPresent(voyage -> {
            // L'état "annule" a l'ID 4 dans la base de données
            etatVoyageService.getById(4L).ifPresent(voyage::setEtatVoyage);
            VoyageService.update(voyage);
        });
        return "redirect:/api/Voyage/list";
    }

    @GetMapping("/edit/{id}")
    public String editVoyage(@PathVariable Long id, Model model) {
        VoyageService.getById(id).ifPresent(voyage -> {
            model.addAttribute("voyage", voyage);
        });
        model.addAttribute("trajets", trajetService.getAll());
        model.addAttribute("chauffeurs", chauffeurService.getAll());
        model.addAttribute("vehicules", vehiculeService.getAll());
        model.addAttribute("etats", etatVoyageService.getAll());
        model.addAttribute("title", "Modifier Voyage");
        model.addAttribute("content", "Voyage/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String updateVoyage(@PathVariable Long id,
                               @RequestParam Long idTrajet,
                               @RequestParam Long idVehicule,
                               @RequestParam Long idChauffeur,
                               @RequestParam Long idEtatVoyage,
                               @RequestParam String dateDepartStr,
                               @RequestParam String heureDepartStr) {
        LocalDate dateDepart = LocalDate.parse(dateDepartStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime heureDepart = LocalTime.parse(heureDepartStr, DateTimeFormatter.ISO_LOCAL_TIME);
        
        VoyageService.getById(id).ifPresent(voyage -> {
            trajetService.getById(idTrajet).ifPresent(voyage::setTrajet);
            vehiculeService.getById(idVehicule).ifPresent(voyage::setVehicule);
            chauffeurService.getById(idChauffeur).ifPresent(voyage::setChauffeur);
            etatVoyageService.getById(idEtatVoyage).ifPresent(voyage::setEtatVoyage);
            voyage.setDateDepart(dateDepart);
            voyage.setHeureDepart(heureDepart);
            VoyageService.update(voyage);
        });
        return "redirect:/api/Voyage/list";
    }
}
