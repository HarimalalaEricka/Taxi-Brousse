package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository PlaceRepository;
    @Autowired
    private VoyageService voyageService;

    public Place create(Place Place) {
        return PlaceRepository.save(Place);
    }

    public List<Place> getAll() {
        return PlaceRepository.findAll();
    }

    public Optional<Place> getById(Long id) {
        return PlaceRepository.findById(id);
    }

    public Place update(Place Place) {
        return PlaceRepository.save(Place);
    }

    public void delete(Long id) {
        PlaceRepository.deleteById(id);
    }

    public List<Place> getPlacesDispo(Long vehiculeId) {
        return PlaceRepository.findByVehiculeIdVehiculeAndStatut(vehiculeId, com.taxi.models.StatusPlace.LIBRE);
    }

     public List<Map<String, Object>> getPlacesParTypePourVehicule(Long vehiculeId) {
        List<Object[]> results = PlaceRepository.countPlacesByTypeForVehicule(vehiculeId);
        List<Map<String, Object>> placesParType = new ArrayList<>();
        
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("typePlace", result[0]);
            map.put("nombrePlaces", result[1]);
            placesParType.add(map);
        }
        
        return placesParType;
    }
}
