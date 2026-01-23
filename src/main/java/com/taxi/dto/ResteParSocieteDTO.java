package com.taxi.dto;

import com.taxi.models.Societe;
import java.math.BigDecimal;

public class ResteParSocieteDTO {
    private Societe societe;
    private BigDecimal montantTotal;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;

    public ResteParSocieteDTO(Societe societe, BigDecimal montantTotal, BigDecimal montantPaye) {
        this.societe = societe;
        this.montantTotal = montantTotal;
        this.montantPaye = montantPaye;
        this.resteAPayer = montantTotal.subtract(montantPaye);
    }

    public Societe getSociete() {
        return societe;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public BigDecimal getResteAPayer() {
        return resteAPayer;
    }
}
