
package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    Page<Vehicule> findByStatut(StatutVehicule statut, Pageable pageable);

    Page<Vehicule> findByAgenceVilleIgnoreCaseAndCategorie(
            String ville, CategorieVehicule categorie, Pageable pageable);

    boolean existsByImmatriculationIgnoreCase(String immatriculation);
}
