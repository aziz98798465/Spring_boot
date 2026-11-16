package tn.esprit.autoloc.service.vehicule;

import tn.esprit.autoloc.domain.Vehicule;

public interface GestionVehiculeEcriture {

    Vehicule ajouter(Vehicule vehicule);

    Vehicule modifier(Long id, Vehicule vehicule);

    void supprimer(Long id);
}
