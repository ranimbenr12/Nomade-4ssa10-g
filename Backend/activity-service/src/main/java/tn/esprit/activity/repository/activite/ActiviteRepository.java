package tn.esprit.activity.repository.activite;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.activity.entity.activite.Activite;
import tn.esprit.activity.entity.activite.Emplacement;
import tn.esprit.activity.entity.activite.TypeActivite;

import java.util.List;

@Repository
public interface ActiviteRepository extends MongoRepository<Activite, String> {

    List<Activite> findByHotelId(Long hotelId);

    List<Activite> findByType(TypeActivite type);

    List<Activite> findByHotelIdAndType(Long hotelId, TypeActivite type);

    List<Activite> findByEmplacement(Emplacement emplacement);

    boolean existsByHotelIdAndNomIgnoreCase(Long hotelId, String nom);

    boolean existsByHotelIdAndNomIgnoreCaseAndIdNot(Long hotelId, String nom, String id);
}