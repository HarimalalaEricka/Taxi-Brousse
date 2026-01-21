package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.taxi.models.*;

@Entity
@Table(name = "categorie_personne")
public class CategoriePersonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriePersonne;

    @Column(name = "categorie")
    private String categorie; 

    @Column(name = "reduction_pourcentage")
    private double reduction_pourcentage;

    @Column(name = "reduction_fixe")
    private double reduction_fixe;

    @ManyToOne
    @JoinColumn(name = "id_type_place", nullable = false)
    private TypePlace typePlace;

    // Getters et setters
    public Long getIdCategoriePersonne() {
        return idCategoriePersonne;
    }

    public void setIdCategoriePersonne(Long idCategoriePersonne) {
        this.idCategoriePersonne = idCategoriePersonne;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getReductionPourcentage() {
        return reduction_pourcentage;
    }

    public void setReductionPourcentage(double reduction_pourcentage) {
        this.reduction_pourcentage = reduction_pourcentage;
    }

    public double getReductionFixe() {
        return reduction_fixe;
    }

    public void setReductionFixe(double reduction_fixe) {
        this.reduction_fixe = reduction_fixe;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }
}