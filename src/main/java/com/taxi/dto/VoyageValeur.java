package com.taxi.dto;

import com.taxi.models.*;

public class VoyageValeur
{
    private Voyage voyage;
    private Double valeurMax;
    private Double chiffreAffaire;
    public VoyageValeur(Voyage voyage, Double valeurMax, Double chiffreAffaire)
    {
        this.voyage = voyage;
        this.valeurMax = valeurMax;
        this.chiffreAffaire = chiffreAffaire;
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
}