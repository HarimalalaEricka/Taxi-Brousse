package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PrixBilletService {

    @Autowired
    private PrixBilletRepository PrixBilletRepository;
    @Autowired 
    private TypePlaceRepository typePlaceRepository;

    public PrixBillet create(PrixBillet PrixBillet) {
        return PrixBilletRepository.save(PrixBillet);
    }

    public List<PrixBillet> getAll() {
        return PrixBilletRepository.findAll();
    }

    public Optional<PrixBillet> getById(Long id) {
        return PrixBilletRepository.findById(id);
    }

    public PrixBillet update(PrixBillet PrixBillet) {
        return PrixBilletRepository.save(PrixBillet);
    }

    public void delete(Long id) {
        PrixBilletRepository.deleteById(id);
    }
    public List<PrixBillet> getPrixByTrajetId(Long trajetId) {
        return PrixBilletRepository.findByTrajetIdTrajet(trajetId);
    }
    public double getLastPrixByTrajetIdAndTypePlace(Long idTrajet, Long idTypePlace) {
    // 1. Récupérer l'Optional
    Optional<TypePlace> typeOptional = typePlaceRepository.findById(idTypePlace);
    
    // 2. Vérifier si présent et extraire l'objet
    if (typeOptional.isPresent()) {
        TypePlace type = typeOptional.get(); // Extrait l'objet de l'Optional
        
        // 3. Maintenant vous pouvez appeler getIdTypePlace() sur l'objet
        Long typeId = type.getIdTypePlace(); // Assurez-vous que cette méthode existe
        
        // 4. Appeler le repository
        return PrixBilletRepository.findLastPrixByTrajetIdTrajetAndTypePlace(idTrajet, typeId);
    } else {
        // Gérer le cas où TypePlace n'est pas trouvé
        throw new RuntimeException("TypePlace non trouvé avec ID: " + idTypePlace);
        // ou retourner une valeur par défaut :
        // return 0.0;
    }

}
public PrixBillet getLastPrixBilletByTrajetAndType(Long trajetId, Long typePlaceId) {
        return PrixBilletRepository.findTopByTrajetIdTrajetAndTypePlaceIdTypePlaceOrderByDateDebutDesc(trajetId, typePlaceId)
                .orElse(null);
    }
}
