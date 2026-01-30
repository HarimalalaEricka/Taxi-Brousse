    


package com.taxi.service;

import com.taxi.dto.ExtraCA;
import com.taxi.models.Extra;
import com.taxi.models.ExtraReservation;
import com.taxi.repository.ExtraRepository;
import com.taxi.repository.ExtraReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class ExtraService {
    @Autowired
    private ExtraRepository extraRepository;
    @Autowired
    private ExtraReservationRepository extraReservationRepository;

    public List<ExtraCA> getChiffreAffaireParExtra() {
        List<Extra> extras = extraRepository.findAll();
        List<ExtraReservation> ventes = extraReservationRepository.findAll();
        Map<Long, Integer> quantites = new HashMap<>();
        for (ExtraReservation vente : ventes) {
            Long id = vente.getExtra().getIdExtra();
            quantites.put(id, quantites.getOrDefault(id, 0) + vente.getQuantite());
        }
        List<ExtraCA> result = new ArrayList<>();
        for (Extra extra : extras) {
            int qte = quantites.getOrDefault(extra.getIdExtra(), 0);
            int ca = qte * (extra.getPrix() != null ? extra.getPrix() : 0);
            result.add(new ExtraCA(extra.getNom(), qte, ca));
        }
        return result;
    }

    public int getChiffreAffaireTotalExtra() {
        return getChiffreAffaireParExtra().stream().mapToInt(ExtraCA::getCa).sum();
    }
        public int getChiffreAffaireStockInitial() {
        return extraRepository.findAll().stream()
            .mapToInt(e -> (e.getStockInitial() != null && e.getPrix() != null) ? e.getStockInitial() * e.getPrix() : 0)
            .sum();
    }
    public int getChiffreAffaireTotalExtraFiltre(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<ExtraReservation> ventes = extraReservationRepository.findAll();
        return ventes.stream()
            .filter(er -> er.getDateReservation() != null &&
                (dateDebut == null || !er.getDateReservation().isBefore(dateDebut)) &&
                (dateFin == null || !er.getDateReservation().isAfter(dateFin)))
            .mapToInt(er -> {
                Extra extra = er.getExtra();
                return (extra != null && extra.getPrix() != null && er.getQuantite() != null)
                    ? extra.getPrix() * er.getQuantite() : 0;
            })
            .sum();
    }
}
