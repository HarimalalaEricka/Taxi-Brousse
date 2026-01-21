package com.taxi.vue;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable // Important : la vue est en lecture seule
@Table(name = "vue_prix_billet")
public class VuePrixBillet {
    
    @Id
    private Long id; // Ou une combinaison unique
    
    private String categorie;
    private String type;
    private Double prix;
    private Long idTrajet;
    private Long idTypePlace;
    private Double prixFinal;
    
    // Constructeurs, getters, setters...
}