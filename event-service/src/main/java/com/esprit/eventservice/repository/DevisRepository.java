package com.esprit.eventservice.repository;

import com.esprit.eventservice.entity.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevisRepository extends JpaRepository<Devis, Long> {
}