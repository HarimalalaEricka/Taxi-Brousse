package com.taxi.controller;

import com.taxi.models.Prestation;
import com.taxi.models.PaiementPrestation;
import com.taxi.dto.PrestationAvecResteDTO;
import com.taxi.service.PrestationService;
import com.taxi.service.SocieteService;
import com.taxi.service.TarifPrestationService;
import com.taxi.service.VoyageService;
import com.taxi.service.PaiementPrestationService;
import com.taxi.repository.EtatPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/prestation")
public class PrestationController {

    @Autowired
    private PrestationService prestationService;

    @Autowired
    private SocieteService societeService;

    @Autowired
    private TarifPrestationService tarifPrestationService;

    @Autowired
    private EtatPaiementRepository etatPaiementRepository;

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private PaiementPrestationService paiementPrestationService;

    @GetMapping
    public String list(@RequestParam(required = false) Integer mois,
                       @RequestParam(required = false) Integer annee,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFiltre,
                       Model model) {
        
        List<Prestation> prestations;
        if (mois != null && annee != null) {
            prestations = prestationService.getByMoisAnnee(mois, annee);
            model.addAttribute("moisSelected", mois);
            model.addAttribute("anneeSelected", annee);
        } else {
            prestations = prestationService.getAll();
        }

        // Toujours créer des DTOs pour avoir le montant payé correct depuis la table paiement_prestation
        List<PrestationAvecResteDTO> prestationsDTO = new ArrayList<>();
        LocalDate dateRef = dateFiltre != null ? dateFiltre : LocalDate.now();
        
        for (Prestation p : prestations) {
            BigDecimal montantPaye = paiementPrestationService.getMontantPayeADate(p, dateRef);
            prestationsDTO.add(new PrestationAvecResteDTO(p, montantPaye));
        }
        
        model.addAttribute("prestationsDTO", prestationsDTO);
        model.addAttribute("dateFiltre", dateFiltre);
        model.addAttribute("useDateFiltre", dateFiltre != null);
        
        // Calculer CA total prestations
        BigDecimal caTotal = prestations.stream()
                .map(Prestation::getMontantTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("caTotal", caTotal);

        // Calculer total montant payé et reste à payer pour les prestations
        BigDecimal totalPaye = prestationsDTO.stream()
                .map(PrestationAvecResteDTO::getMontantPaye)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalResteAPayer = prestationsDTO.stream()
                .map(PrestationAvecResteDTO::getResteAPayer)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalPaye", totalPaye);
        model.addAttribute("totalResteAPayer", totalResteAPayer);

        model.addAttribute("title", "Prestations");
        model.addAttribute("content", "Prestation/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("prestation", new Prestation());
        model.addAttribute("societes", societeService.getAll());
        model.addAttribute("tarifPrestations", tarifPrestationService.getAll());
        model.addAttribute("voyages", voyageService.getAll());
        model.addAttribute("title", "Nouvelle Prestation");
        model.addAttribute("content", "Prestation/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Prestation prestation) {
        etatPaiementRepository.findByEtat("non paye").ifPresent(prestation::setEtatPaiement);
        prestationService.create(prestation);
        return "redirect:/prestation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        prestationService.delete(id);
        return "redirect:/prestation";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        prestationService.getById(id).ifPresent(prestation -> {
            model.addAttribute("prestation", prestation);
        });
        model.addAttribute("societes", societeService.getAll());
        model.addAttribute("tarifPrestations", tarifPrestationService.getAll());
        model.addAttribute("voyages", voyageService.getAll());
        model.addAttribute("title", "Modifier Prestation");
        model.addAttribute("content", "Prestation/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Prestation prestation) {
        prestationService.getById(id).ifPresent(existing -> {
            prestation.setIdPrestation(id);
            prestation.setEtatPaiement(existing.getEtatPaiement());
            prestationService.update(prestation);
        });
        return "redirect:/prestation";
    }

    @GetMapping("/payer/{id}")
    public String payerForm(@PathVariable Long id, Model model) {
        prestationService.getById(id).ifPresent(prestation -> {
            BigDecimal montantPaye = paiementPrestationService.getMontantPaye(prestation);
            BigDecimal resteAPayer = prestation.getMontantTotal().subtract(montantPaye);
            model.addAttribute("prestation", prestation);
            model.addAttribute("montantPaye", montantPaye);
            model.addAttribute("resteAPayer", resteAPayer);
            model.addAttribute("paiements", paiementPrestationService.getByPrestation(prestation));
        });
        model.addAttribute("title", "Paiement Prestation");
        model.addAttribute("content", "Prestation/payer");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/payer/{id}")
    public String payer(@PathVariable Long id, 
                        @RequestParam BigDecimal montant,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePaiement) {
        prestationService.getById(id).ifPresent(prestation -> {
            PaiementPrestation paiement = new PaiementPrestation(prestation, montant, datePaiement);
            paiementPrestationService.create(paiement);
        });
        return "redirect:/prestation";
    }

    @GetMapping("/historique")
    public String historiquePaiements(Model model) {
        List<PaiementPrestation> paiements = paiementPrestationService.getAll();
        
        BigDecimal totalPaiements = paiements.stream()
                .map(PaiementPrestation::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("totalPaiements", totalPaiements);
        model.addAttribute("title", "Historique des Paiements");
        model.addAttribute("content", "Prestation/historique");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }
}
