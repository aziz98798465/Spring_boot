
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

    ReservationDTO ajouterReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> afficherReservations();

    Optional<ReservationDTO> afficherReservationParId(Long id);

    ReservationDTO modifierReservation(Long id, ReservationDTO reservationDTO);

    void supprimerReservation(Long id);
}
