package com.taxi.controller;

import com.taxi.dto.ChiffreAffaireDTO;
import com.taxi.service.VoyageService;
import com.taxi.service.PrestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class HomeController {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private PrestationService prestationService;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) Integer mois,
            @RequestParam(required = false) Integer annee,
            Model model) {
        
        // Valeurs par défaut : mois et année actuels
        LocalDate now = LocalDate.now();
        int moisSelected = (mois != null) ? mois : now.getMonthValue();
        int anneeSelected = (annee != null) ? annee : now.getYear();
        
        // Calcul du CA Voyages
        double caVoyages = voyageService.calculerCAVoyages(moisSelected, anneeSelected);
        
        // Calcul du CA Prestations
        BigDecimal caPrestations = prestationService.calculerCA(moisSelected, anneeSelected);
        
        // CA Total
        BigDecimal caTotal = BigDecimal.valueOf(caVoyages).add(caPrestations);
        
        ChiffreAffaireDTO chiffreAffaire = new ChiffreAffaireDTO(
            moisSelected, 
            anneeSelected, 
            BigDecimal.valueOf(caVoyages), 
            caPrestations
        );
        
        model.addAttribute("chiffreAffaire", chiffreAffaire);
        model.addAttribute("moisSelected", moisSelected);
        model.addAttribute("anneeSelected", anneeSelected);
        
        // Statistiques
        var voyages = voyageService.getByMoisAnnee(moisSelected, anneeSelected);
        var prestations = prestationService.getByMoisAnnee(moisSelected, anneeSelected);
        
        model.addAttribute("nbVoyages", voyages.size());
        model.addAttribute("nbPrestations", prestations.size());
        model.addAttribute("nbVehicules", voyageService.getAll().stream().map(v -> v.getVehicule().getIdVehicule()).distinct().count());
        model.addAttribute("nbSocietes", prestations.stream().map(p -> p.getSociete().getIdSociete()).distinct().count());
        
        model.addAttribute("title", "Accueil - Tableau de Bord");
        model.addAttribute("content", "index");
        model.addAttribute("fragment", "content");
        return "layout";
    }
}
