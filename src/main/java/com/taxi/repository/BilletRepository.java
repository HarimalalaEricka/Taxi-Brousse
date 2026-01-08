package com.taxi.repository;

import com.taxi.models.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
}
