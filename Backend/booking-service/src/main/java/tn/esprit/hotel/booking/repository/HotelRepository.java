package tn.esprit.hotel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.hotel.booking.entity.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    List<Hotel> findByVilleIgnoreCase(String ville);
}