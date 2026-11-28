package tn.esprit.hotel.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.hotel.booking.dto.HotelRequest;
import tn.esprit.hotel.booking.dto.HotelResponse;
import tn.esprit.hotel.booking.entity.Hotel;
import tn.esprit.hotel.booking.exception.ResourceNotFoundException;
import tn.esprit.hotel.booking.repository.HotelRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelResponse creer(HotelRequest request) {
        Hotel hotel = new Hotel();
        appliquer(hotel, request);
        return HotelResponse.from(hotelRepository.save(hotel));
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> lister(String ville) {
        List<Hotel> hotels = (ville == null || ville.isBlank())
                ? hotelRepository.findAll()
                : hotelRepository.findByVilleIgnoreCase(ville);
        return hotels.stream().map(HotelResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public HotelResponse trouver(UUID id) {
        return HotelResponse.from(chercher(id));
    }

    public HotelResponse modifier(UUID id, HotelRequest request) {
        Hotel hotel = chercher(id);
        appliquer(hotel, request);
        return HotelResponse.from(hotel);
    }

    public void supprimer(UUID id) {
        hotelRepository.delete(chercher(id));
    }

    private Hotel chercher(UUID id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hôtel introuvable : " + id));
    }

    private void appliquer(Hotel hotel, HotelRequest r) {
        hotel.setNom(r.nom());
        hotel.setAdresse(r.adresse());
        hotel.setVille(r.ville());
        hotel.setEtoiles(r.etoiles());
        hotel.setPartnerId(r.partnerId());
    }
}