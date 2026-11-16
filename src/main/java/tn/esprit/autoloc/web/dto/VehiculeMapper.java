
package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Vehicule;

public class VehiculeMapper {

    private VehiculeMapper() {
        // Classe contenant uniquement des méthodes de conversion.
    }

    // Convertir Vehicule en VehiculeDTO
    public static VehiculeDTO toDTO(Vehicule vehicule) {
        if (vehicule == null) {
            return null;
        }

        VehiculeDTO dto = new VehiculeDTO();

        dto.setIdVehicule(vehicule.getIdVehicule());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setCategorie(vehicule.getCategorie());
        dto.setTarifJournalier(vehicule.getTarifJournalier());
        dto.setStatut(vehicule.getStatut());

        return dto;
    }

    // Convertir VehiculeDTO en Vehicule
    public static Vehicule toEntity(VehiculeDTO dto) {
        if (dto == null) {
            return null;
        }

        Vehicule vehicule = new Vehicule();

        vehicule.setIdVehicule(dto.getIdVehicule());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setStatut(dto.getStatut());

        return vehicule;
    }
}
