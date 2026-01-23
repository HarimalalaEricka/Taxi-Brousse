package com.taxi.controller;

import com.taxi.models.TarifPrestation;
import com.taxi.service.TarifPrestationService;
import com.taxi.service.TypePrestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/tarif-prestation")
public class TarifPrestationController {

    @Autowired
    private TarifPrestationService tarifPrestationService;

    @Autowired
    private TypePrestationService typePrestationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tarifPrestations", tarifPrestationService.getAll());
        model.addAttribute("title", "Tarifs Prestations");
        model.addAttribute("content", "TarifPrestation/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("tarifPrestation", new TarifPrestation());
        model.addAttribute("typePrestations", typePrestationService.getAll());
        model.addAttribute("title", "Nouveau Tarif");
        model.addAttribute("content", "TarifPrestation/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TarifPrestation tarifPrestation) {
        tarifPrestationService.create(tarifPrestation);
        return "redirect:/tarif-prestation";
    }

    @GetMapping("/cloturer/{id}")
    public String cloturer(@PathVariable Long id) {
        tarifPrestationService.getById(id).ifPresent(tarif -> {
            tarif.setDateFin(LocalDate.now());
            tarifPrestationService.update(tarif);
        });
        return "redirect:/tarif-prestation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        tarifPrestationService.delete(id);
        return "redirect:/tarif-prestation";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        tarifPrestationService.getById(id).ifPresent(tarif -> {
            model.addAttribute("tarifPrestation", tarif);
        });
        model.addAttribute("typePrestations", typePrestationService.getAll());
        model.addAttribute("title", "Modifier Tarif");
        model.addAttribute("content", "TarifPrestation/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute TarifPrestation tarifPrestation) {
        tarifPrestationService.getById(id).ifPresent(existing -> {
            existing.setPrixUnitaire(tarifPrestation.getPrixUnitaire());
            existing.setDateDebut(tarifPrestation.getDateDebut());
            existing.setDateFin(tarifPrestation.getDateFin());
            existing.setTypePrestation(tarifPrestation.getTypePrestation());
            tarifPrestationService.update(existing);
        });
        return "redirect:/tarif-prestation";
    }
}
