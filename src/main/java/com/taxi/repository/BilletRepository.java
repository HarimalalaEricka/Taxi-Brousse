package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
    List<Billet> findByReservation(Reservation reservation);
}
