package com.taxi.dto;

import java.math.BigDecimal;

public class ChiffreAffaireDTO {
    private int mois;
    private int annee;
    private BigDecimal caVoyages;
    private BigDecimal caPrestations;
    private BigDecimal caTotal;

    public ChiffreAffaireDTO() {}

    public ChiffreAffaireDTO(int mois, int annee, BigDecimal caVoyages, BigDecimal caPrestations) {
        this.mois = mois;
        this.annee = annee;
        this.caVoyages = caVoyages;
        this.caPrestations = caPrestations;
        this.caTotal = caVoyages.add(caPrestations);
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public BigDecimal getCaVoyages() {
        return caVoyages;
    }

    public void setCaVoyages(BigDecimal caVoyages) {
        this.caVoyages = caVoyages;
    }

    public BigDecimal getCaPrestations() {
        return caPrestations;
    }

    public void setCaPrestations(BigDecimal caPrestations) {
        this.caPrestations = caPrestations;
    }

    public BigDecimal getCaTotal() {
        return caTotal;
    }

    public void setCaTotal(BigDecimal caTotal) {
        this.caTotal = caTotal;
    }
}
