package com.taxi.dto;

public class ExtraCA {
    private String nom;
    private int quantiteVendue;
    private int ca;

    public ExtraCA(String nom, int quantiteVendue, int ca) {
        this.nom = nom;
        this.quantiteVendue = quantiteVendue;
        this.ca = ca;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getQuantiteVendue() { return quantiteVendue; }
    public void setQuantiteVendue(int quantiteVendue) { this.quantiteVendue = quantiteVendue; }
    public int getCa() { return ca; }
    public void setCa(int ca) { this.ca = ca; }
}
