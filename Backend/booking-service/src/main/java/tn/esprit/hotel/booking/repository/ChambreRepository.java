package tn.esprit.hotel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.hotel.booking.entity.Chambre;

import java.util.List;
import java.util.UUID;

public interface ChambreRepository extends JpaRepository<Chambre, UUID> {

    List<Chambre> findByHotelId(UUID hotelId);
    boolean existsByHotelIdAndNumero(UUID hotelId, String numero);
}