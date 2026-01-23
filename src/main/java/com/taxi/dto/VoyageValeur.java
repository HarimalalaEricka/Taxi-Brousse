package com.taxi.dto;

import com.taxi.models.*;

public class VoyageValeur
{
    private Voyage voyage;
    private Double valeurMax;
    private Double chiffreAffaire;
    private Double caPrestation;

    public VoyageValeur(Voyage voyage, Double valeurMax, Double chiffreAffaire, Double caPrestation)
    {
        this.voyage = voyage;
        this.valeurMax = valeurMax;
        this.chiffreAffaire = chiffreAffaire;
        this.caPrestation = caPrestation;
    }
    public Voyage getVoyage() {
        return voyage;
    }
    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }
    public Double getValeurMax() {
        return valeurMax;
    }
    public void setValeurMax(Double valeurMax) {
        this.valeurMax = valeurMax;
    }
    public Double getChiffreAffaire()
    {
        return chiffreAffaire;
    }
    public void setChiffreAffaire( Double chiffreAffaire)
    {
        this.chiffreAffaire = chiffreAffaire;
    }
    public Double getCaPrestation() {
        return caPrestation;
    }
    public void setCaPrestation(Double caPrestation) {
        this.caPrestation = caPrestation;
    }
}