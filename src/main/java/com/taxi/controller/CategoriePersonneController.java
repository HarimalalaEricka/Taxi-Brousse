package com.taxi.controller;

import com.taxi.models.CategoriePersonne;
import com.taxi.models.TypePlace;
import com.taxi.service.CategoriePersonneService;
import com.taxi.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/api/CategoriePersonne")
public class CategoriePersonneController {

    @Autowired
    private CategoriePersonneService categoriePersonneService;

    @Autowired
    private TypePlaceRepository typePlaceRepository;

    // Liste des catégories
    @GetMapping("/list")
    public String getAll(Model model) {
        List<CategoriePersonne> categories = categoriePersonneService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Catégories de Personnes");
        model.addAttribute("content", "CategoriePersonne/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    // Formulaire de création
    @GetMapping("/create")
    public String createForm(Model model) {
        List<TypePlace> typePlaces = typePlaceRepository.findAll();
        model.addAttribute("typePlaces", typePlaces);
        model.addAttribute("title", "Nouvelle Catégorie");
        model.addAttribute("content", "CategoriePersonne/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    // Soumettre création
    @PostMapping("/create")
    public String createSubmit(@RequestParam String categorie,
                               @RequestParam double reductionPourcentage,
                               @RequestParam double reductionFixe,
                               @RequestParam Long typePlaceId,
                               Model model) {
        CategoriePersonne cat = new CategoriePersonne();
        cat.setCategorie(categorie);
        cat.setReductionPourcentage(reductionPourcentage);
        cat.setReductionFixe(reductionFixe);
        
        TypePlace typePlace = typePlaceRepository.findById(typePlaceId).orElse(null);
        cat.setTypePlace(typePlace);

        categoriePersonneService.create(cat);
        return "redirect:/api/CategoriePersonne/list";
    }

    // Formulaire d'édition
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        categoriePersonneService.getById(id).ifPresent(cat -> {
            model.addAttribute("categoriePersonne", cat);
        });
        List<TypePlace> typePlaces = typePlaceRepository.findAll();
        model.addAttribute("typePlaces", typePlaces);
        model.addAttribute("title", "Modifier Catégorie");
        model.addAttribute("content", "CategoriePersonne/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    // Soumettre modification
    @PostMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String categorie,
                         @RequestParam double reductionPourcentage,
                         @RequestParam double reductionFixe,
                         @RequestParam Long typePlaceId,
                         Model model) {
        CategoriePersonne cat = categoriePersonneService.getById(id).orElse(new CategoriePersonne());
        cat.setIdCategoriePersonne(id);
        cat.setCategorie(categorie);
        cat.setReductionPourcentage(reductionPourcentage);
        cat.setReductionFixe(reductionFixe);
        
        TypePlace typePlace = typePlaceRepository.findById(typePlaceId).orElse(null);
        cat.setTypePlace(typePlace);

        categoriePersonneService.update(cat);
        return "redirect:/api/CategoriePersonne/list";
    }

    // Supprimer
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoriePersonneService.delete(id);
        return "redirect:/api/CategoriePersonne/list";
    }
}
