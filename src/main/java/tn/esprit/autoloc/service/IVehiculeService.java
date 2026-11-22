package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.service.vehicule.GestionVehiculeEcriture;
import tn.esprit.autoloc.service.vehicule.GestionVehiculeLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVehiculeService
        extends GestionVehiculeLecture, GestionVehiculeEcriture {

    double calculerTarif(Vehicule vehicule, int nombreJours);

    Page<Vehicule> rechercherDisponibles(Pageable pageable);

    Page<Vehicule> rechercherParVilleEtCategorie(
            String ville, CategorieVehicule categorie, Pageable pageable);
}
