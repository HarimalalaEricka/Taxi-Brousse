package com.taxi.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "extra")
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExtra;

    private String nom;
    private Integer prix;
    private Integer stockInitial;

    // Getters et setters
    public Long getIdExtra() { return idExtra; }
    public void setIdExtra(Long idExtra) { this.idExtra = idExtra; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Integer getPrix() { return prix; }
    public void setPrix(Integer prix) { this.prix = prix; }
    public Integer getStockInitial() { return stockInitial; }
    public void setStockInitial(Integer stockInitial) { this.stockInitial = stockInitial; }
}
