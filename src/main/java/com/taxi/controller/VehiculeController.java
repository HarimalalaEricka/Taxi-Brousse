package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.*;

// @RestController
@Controller
@RequestMapping("/api/Vehicule") // à adapter pour chaque entité, ex: /api/voyages
public class VehiculeController {

    @Autowired
    private VehiculeService VehiculeService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private TypePlaceService typePlaceService;
    @Autowired
    private EtatVehiculeService etatVehiculeService;

    // Créer une entité
    @PostMapping
    @ResponseBody
    public Vehicule create(@RequestBody Vehicule Vehicule) {
        return VehiculeService.create(Vehicule);
    }

    // Lire toutes les entités
    // @GetMapping("/list")
    // public List<Vehicule> getAll(Model model) {
    //     List<Vehicule> vehicules = VehiculeService.getAll();
    //     model.addAttribute("vehicules", vehicules);
    //     return "Vehicule/list"
    // }
    @GetMapping("/list")
    public String getAll(Model model) {
        List<Vehicule> vehicules = VehiculeService.getAll();
        List<Map<String, Object>> vehiculesDetails = new ArrayList<>();
        
        for (Vehicule vehicule : vehicules) {
            Map<String, Object> vehiculeDetail = new HashMap<>();
            vehiculeDetail.put("vehicule", vehicule);
            
            List<Map<String, Object>> placesParType = placeService.getPlacesParTypePourVehicule(vehicule.getIdVehicule());
            vehiculeDetail.put("placesParType", placesParType);
            
            vehiculesDetails.add(vehiculeDetail);
        }
        
        model.addAttribute("vehiculesDetails", vehiculesDetails);
         model.addAttribute("title", "Vehicule");
        model.addAttribute("content", "Vehicule/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "vehicule-list.css");
        return "layout";
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    @ResponseBody
    public Vehicule getById(@PathVariable Long id) {
        return VehiculeService.getById(id).orElse(null);
    }

    // Mettre à jour un Vehicule
    @PutMapping("/{id}")
    @ResponseBody
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule updatedVehicule) {
        updatedVehicule.setIdVehicule(id);
        return VehiculeService.update(updatedVehicule);
    }

    // Supprimer un Vehicule
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        VehiculeService.delete(id);
    }

    @GetMapping("/createVehicule")
public String createVehiculeForm(Model model) {
    List<TypePlace> types = typePlaceService.getAll();
    List<EtatVehicule> etats = etatVehiculeService.getAll(); // Ajouter cette ligne
    
    model.addAttribute("types", types);
    model.addAttribute("etats", etats); // Ajouter cette ligne
    model.addAttribute("title", "Vehicule");
    model.addAttribute("content", "Vehicule/create_vehicule");
    model.addAttribute("fragment", "content");
    model.addAttribute("pageCss", "input.css");
    return "layout";
}

@PostMapping("/createVehicule")
public String createVehiculeSubmit(@RequestParam String immatriculation,
                                   @RequestParam Integer nombrePlaces,
                                   @RequestParam Map<String, String> allParams) {
    
    // Récupérer l'état "disponible" par défaut (vous devez créer ce service)
    EtatVehicule etatVehicule = etatVehiculeService.getByStatus("disponible")
            .orElseGet(() -> {
                // Créer l'état s'il n'existe pas
                EtatVehicule newEtat = new EtatVehicule();
                newEtat.setStatus("disponible");
                return etatVehiculeService.create(newEtat);
            });
    
    // Créer le véhicule
    Vehicule vehicule = new Vehicule();
    vehicule.setImmatriculation(immatriculation);
    vehicule.setNombrePlaces(nombrePlaces);
    vehicule.setEtatVehicule(etatVehicule);
    
    Vehicule createdVehicule = VehiculeService.create(vehicule);

    // Gérer les places (même code qu'avant)
    for (Map.Entry<String, String> entry : allParams.entrySet()) {
        if (entry.getKey().startsWith("typePlace_")) {
            Long typePlaceId = Long.parseLong(entry.getKey().substring("typePlace_".length()));
            Integer nombre = Integer.parseInt(entry.getValue());

            for (int i = 0; i < nombre; i++) {
                Place place = new Place();
                place.setVehicule(createdVehicule);
                TypePlace typePlace = typePlaceService.getById(typePlaceId).orElse(null);
                place.setTypePlace(typePlace);
                place.setStatut(StatusPlace.LIBRE);
                place.generererNumeroPlace();
                placeService.create(place);
            }
        }
    }

    return "redirect:/api/Vehicule/list";
}

    @GetMapping("/delete/{id}")
    public String deleteVehicule(@PathVariable Long id) {
        VehiculeService.delete(id);
        return "redirect:/api/Vehicule/list";
    }

    @GetMapping("/horsService/{id}")
    public String horsServiceVehicule(@PathVariable Long id) {
        VehiculeService.getById(id).ifPresent(vehicule -> {
            etatVehiculeService.getByStatus("hors service").ifPresent(vehicule::setEtatVehicule);
            VehiculeService.update(vehicule);
        });
        return "redirect:/api/Vehicule/list";
    }

    @GetMapping("/disponible/{id}")
    public String disponibleVehicule(@PathVariable Long id) {
        VehiculeService.getById(id).ifPresent(vehicule -> {
            etatVehiculeService.getByStatus("disponible").ifPresent(vehicule::setEtatVehicule);
            VehiculeService.update(vehicule);
        });
        return "redirect:/api/Vehicule/list";
    }

    @GetMapping("/edit/{id}")
    public String editVehicule(@PathVariable Long id, Model model) {
        VehiculeService.getById(id).ifPresent(vehicule -> {
            model.addAttribute("vehicule", vehicule);
        });
        model.addAttribute("etats", etatVehiculeService.getAll());
        model.addAttribute("title", "Modifier Véhicule");
        model.addAttribute("content", "Vehicule/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String updateVehicule(@PathVariable Long id,
                                 @RequestParam String immatriculation,
                                 @RequestParam Integer nombrePlaces,
                                 @RequestParam Long idEtatVehicule) {
        VehiculeService.getById(id).ifPresent(vehicule -> {
            vehicule.setImmatriculation(immatriculation);
            vehicule.setNombrePlaces(nombrePlaces);
            etatVehiculeService.getById(idEtatVehicule).ifPresent(vehicule::setEtatVehicule);
            VehiculeService.update(vehicule);
        });
        return "redirect:/api/Vehicule/list";
    }
}
