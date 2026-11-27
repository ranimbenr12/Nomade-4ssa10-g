package tn.esprit.activity.service.activite;

import org.springframework.stereotype.Service;
import tn.esprit.activity.common.exception.ConflictException;
import tn.esprit.activity.common.exception.ResourceNotFoundException;
import tn.esprit.activity.dto.activite.ActiviteRequest;
import tn.esprit.activity.dto.activite.ActiviteResponse;
import tn.esprit.activity.entity.activite.Activite;
import tn.esprit.activity.entity.activite.TypeActivite;
import tn.esprit.activity.mapper.activite.ActiviteMapper;
import tn.esprit.activity.repository.activite.ActiviteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;

    public ActiviteService(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

    /** Liste les activités, avec filtres optionnels hotelId et type */
    public List<ActiviteResponse> findAll(Long hotelId, TypeActivite type) {
        List<Activite> activites;
        if (hotelId != null && type != null) {
            activites = activiteRepository.findByHotelIdAndType(hotelId, type);
        } else if (hotelId != null) {
            activites = activiteRepository.findByHotelId(hotelId);
        } else if (type != null) {
            activites = activiteRepository.findByType(type);
        } else {
            activites = activiteRepository.findAll();
        }
        return activites.stream().map(ActiviteMapper::toResponse).toList();
    }

    public ActiviteResponse findById(String id) {
        return ActiviteMapper.toResponse(getActivite(id));
    }

    public ActiviteResponse create(ActiviteRequest request) {
        if (activiteRepository.existsByHotelIdAndNomIgnoreCase(request.hotelId(), request.nom().trim())) {
            throw new ConflictException("L'hôtel " + request.hotelId()
                    + " a déjà une activité nommée \"" + request.nom().trim() + "\"");
        }
        Activite activite = ActiviteMapper.toEntity(request);
        activite.setDateCreation(LocalDateTime.now());
        activite.setDateModification(LocalDateTime.now());
        return ActiviteMapper.toResponse(activiteRepository.save(activite));
    }

    public ActiviteResponse update(String id, ActiviteRequest request) {
        Activite activite = getActivite(id);
        if (activiteRepository.existsByHotelIdAndNomIgnoreCaseAndIdNot(request.hotelId(), request.nom().trim(), id)) {
            throw new ConflictException("L'hôtel " + request.hotelId()
                    + " a déjà une autre activité nommée \"" + request.nom().trim() + "\"");
        }
        ActiviteMapper.updateEntity(activite, request);
        activite.setDateModification(LocalDateTime.now());
        return ActiviteMapper.toResponse(activiteRepository.save(activite));
    }

    public void delete(String id) {
        activiteRepository.delete(getActivite(id));
    }

    /** Récupère l'activité ou lance une erreur 404 */
    public Activite getActivite(String id) {
        return activiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activité", id));
    }
}