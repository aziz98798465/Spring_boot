package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.vehicule.GestionVehiculeEcriture;
import tn.esprit.autoloc.service.vehicule.GestionVehiculeLecture;

public interface IVehiculeService
        extends GestionVehiculeLecture, GestionVehiculeEcriture {

    double calculerTarif(Vehicule vehicule, int nombreJours);
}
