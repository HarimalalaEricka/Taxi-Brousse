package com.taxi.repository;

import com.taxi.models.TarifPrestation;
import com.taxi.models.TypePrestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarifPrestationRepository extends JpaRepository<TarifPrestation, Long> {
    List<TarifPrestation> findByTypePrestationAndDateFinIsNull(TypePrestation typePrestation);
}
