package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "extra_achat")
public class ExtraAchat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAchat;

    @ManyToOne
    @JoinColumn(name = "id_extra")
    private Extra extra;

    private Integer quantite;
    private LocalDate dateAchat;

    // Getters et setters
    public Long getIdAchat() { return idAchat; }
    public void setIdAchat(Long idAchat) { this.idAchat = idAchat; }
    public Extra getExtra() { return extra; }
    public void setExtra(Extra extra) { this.extra = extra; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public LocalDate getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDate dateAchat) { this.dateAchat = dateAchat; }
}
