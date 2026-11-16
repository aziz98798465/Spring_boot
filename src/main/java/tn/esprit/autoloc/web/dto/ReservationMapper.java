
package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Reservation;

public class ReservationMapper {

    private ReservationMapper() {
        // Classe utilitaire de conversion.
    }

    public static ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDTO dto = new ReservationDTO();

        dto.setIdReservation(reservation.getIdReservation());
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setStatut(reservation.getStatut());

        if (reservation.getClient() != null) {
            dto.setIdClient(reservation.getClient().getIdClient());
        }

        if (reservation.getVehicule() != null) {
            dto.setIdVehicule(reservation.getVehicule().getIdVehicule());
        }

        return dto;
    }
}
