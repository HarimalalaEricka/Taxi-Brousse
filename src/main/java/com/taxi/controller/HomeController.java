package com.taxi.controller;

import com.taxi.dto.ChiffreAffaireDTO;
import com.taxi.service.VoyageService;
import com.taxi.service.PrestationService;
import com.taxi.service.ExtraService;
import com.taxi.dto.ExtraCA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private PrestationService prestationService;

    @Autowired
    private ExtraService extraService;

    @GetMapping("/")
            public String home(
                @RequestParam(required = false) Integer mois,
                @RequestParam(required = false) Integer annee,
                @RequestParam(required = false) String dateDebut,
                @RequestParam(required = false) String dateFin,
                @RequestParam(required = false) String typeCA,
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

        // Dates et typeCA pour filtre (pour affichage dans le formulaire)
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("typeCA", typeCA);

        // Conversion des dates String -> LocalDateTime
        LocalDateTime dtDebut = null;
        LocalDateTime dtFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm:ss]");
        try {
            if (dateDebut != null && !dateDebut.isEmpty()) dtDebut = LocalDateTime.parse(dateDebut + "T00:00:00", formatter);
            if (dateFin != null && !dateFin.isEmpty()) dtFin = LocalDateTime.parse(dateFin + "T23:59:59", formatter);
        } catch (Exception e) { /* ignore parse errors */ }

        // Chiffre d'affaires par extra (non filtré)
        List<ExtraCA> caExtras = extraService.getChiffreAffaireParExtra();
        model.addAttribute("caExtras", caExtras);
        // CA extra filtré par date
        int caTotalExtra = extraService.getChiffreAffaireTotalExtraFiltre(dtDebut, dtFin);
        model.addAttribute("caTotalExtra", caTotalExtra);
        // CA stock initial : n'afficher que si la période filtrée inclut le stock (sinon 0)
        int caStockInitial = (dtDebut == null && dtFin == null) ? extraService.getChiffreAffaireStockInitial() : 0;
        model.addAttribute("caStockInitial", caStockInitial);
        
        model.addAttribute("title", "Accueil - Tableau de Bord");
        model.addAttribute("content", "index");
        model.addAttribute("fragment", "content");
        return "layout";
    }
}
