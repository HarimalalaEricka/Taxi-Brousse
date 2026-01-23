package com.taxi.dto;

import com.taxi.models.Prestation;
import java.math.BigDecimal;

public class PrestationAvecResteDTO {
    
    private Prestation prestation;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;

    public PrestationAvecResteDTO(Prestation prestation, BigDecimal montantPaye) {
        this.prestation = prestation;
        this.montantPaye = montantPaye;
        this.resteAPayer = prestation.getMontantTotal().subtract(montantPaye);
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getResteAPayer() {
        return resteAPayer;
    }

    public void setResteAPayer(BigDecimal resteAPayer) {
        this.resteAPayer = resteAPayer;
    }

    public BigDecimal getMontantTotal() {
        return prestation.getMontantTotal();
    }
}
