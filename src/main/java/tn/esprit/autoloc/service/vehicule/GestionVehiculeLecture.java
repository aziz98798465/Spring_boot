package tn.esprit.autoloc.service.vehicule;

import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;
import java.util.Optional;

public interface GestionVehiculeLecture {

    List<Vehicule> afficherTous();

    Optional<Vehicule> afficherParId(Long id);
}
