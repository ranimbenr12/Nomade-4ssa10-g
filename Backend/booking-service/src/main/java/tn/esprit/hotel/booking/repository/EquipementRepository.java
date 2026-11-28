package tn.esprit.hotel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.hotel.booking.entity.Equipement;

import java.util.UUID;

public interface EquipementRepository extends JpaRepository<Equipement, UUID> {
}