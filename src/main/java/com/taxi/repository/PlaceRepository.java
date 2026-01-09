package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByVehiculeIdVehiculeAndStatut(Long idVehicule, StatusPlace statut);
}
