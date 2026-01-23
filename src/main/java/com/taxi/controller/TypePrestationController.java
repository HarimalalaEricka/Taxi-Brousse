package com.taxi.controller;

import com.taxi.models.TypePrestation;
import com.taxi.service.TypePrestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/type-prestation")
public class TypePrestationController {

    @Autowired
    private TypePrestationService typePrestationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("typePrestations", typePrestationService.getAll());
        model.addAttribute("title", "Types de Prestation");
        model.addAttribute("content", "TypePrestation/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("typePrestation", new TypePrestation());
        model.addAttribute("title", "Nouveau Type");
        model.addAttribute("content", "TypePrestation/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TypePrestation typePrestation) {
        typePrestationService.create(typePrestation);
        return "redirect:/type-prestation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        typePrestationService.delete(id);
        return "redirect:/type-prestation";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        typePrestationService.getById(id).ifPresent(typePrestation -> {
            model.addAttribute("typePrestation", typePrestation);
        });
        model.addAttribute("title", "Modifier Type de Prestation");
        model.addAttribute("content", "TypePrestation/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute TypePrestation typePrestation) {
        typePrestation.setIdTypePrestation(id);
        typePrestationService.update(typePrestation);
        return "redirect:/type-prestation";
    }
}
