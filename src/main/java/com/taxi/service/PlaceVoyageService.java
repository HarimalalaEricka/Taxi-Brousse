package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceVoyageService {

    @Autowired
    private PlaceVoyageRepository placeVoyageRepository;

    @Autowired
    private PlaceRepository placeRepository;

    public PlaceVoyage create(PlaceVoyage placeVoyage) {
        return placeVoyageRepository.save(placeVoyage);
    }

    public List<PlaceVoyage> getAll() {
        return placeVoyageRepository.findAll();
    }

    public Optional<PlaceVoyage> getById(Long id) {
        return placeVoyageRepository.findById(id);
    }

    public PlaceVoyage update(PlaceVoyage placeVoyage) {
        return placeVoyageRepository.save(placeVoyage);
    }

    public void delete(Long id) {
        placeVoyageRepository.deleteById(id);
    }

    /**
     * Récupère les places disponibles (LIBRE) pour un voyage spécifique
     */
    public List<PlaceVoyage> getPlacesDispoForVoyage(Long voyageId) {
        return placeVoyageRepository.findByVoyageIdVoyageAndStatut(voyageId, StatusPlace.LIBRE);
    }

    /**
     * Récupère toutes les PlaceVoyage pour un voyage
     */
    public List<PlaceVoyage> getPlacesByVoyage(Long voyageId) {
        return placeVoyageRepository.findByVoyageIdVoyage(voyageId);
    }

    /**
     * Initialise les PlaceVoyage pour un nouveau voyage
     * Copie toutes les places du véhicule et les met en statut LIBRE
     */
    public void initializePlacesForVoyage(Voyage voyage) {
        if (voyage == null || voyage.getVehicule() == null) {
            throw new RuntimeException("Voyage ou véhicule non valide");
        }

        Long vehiculeId = voyage.getVehicule().getIdVehicule();
        List<Place> placesVehicule = placeRepository.findByVehicule(voyage.getVehicule());

        for (Place place : placesVehicule) {
            PlaceVoyage pv = new PlaceVoyage();
            pv.setPlace(place);
            pv.setVoyage(voyage);
            pv.setStatut(StatusPlace.LIBRE);
            placeVoyageRepository.save(pv);
        }
    }

    /**
     * Trouve une PlaceVoyage par place et voyage
     */
    public Optional<PlaceVoyage> findByPlaceAndVoyage(Place place, Voyage voyage) {
        return placeVoyageRepository.findByPlaceAndVoyage(place, voyage);
    }

    /**
     * Compte les places disponibles pour un voyage
     */
    public long countPlacesDispoForVoyage(Long voyageId) {
        return placeVoyageRepository.countByVoyageIdVoyageAndStatut(voyageId, StatusPlace.LIBRE);
    }
}
