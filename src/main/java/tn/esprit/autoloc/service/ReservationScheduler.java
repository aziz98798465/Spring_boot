package tn.esprit.autoloc.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationScheduler {

    private final ReservationRepository reservationRepository;

    public ReservationScheduler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "${autoloc.scheduler.release-cron:0 0 2 * * *}")
    @Transactional
    public void libererVehiculesEnFinDeLocation() {
        List<Reservation> terminees =
                reservationRepository.findByDateFinBeforeAndStatut(
                        LocalDate.now(), StatutReservation.CONFIRMEE);

        for (Reservation reservation : terminees) {
            reservation.setStatut(StatutReservation.TERMINEE);
            if (reservation.getVehicule() != null) {
                reservation.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
            }
        }

        if (!terminees.isEmpty()) {
            reservationRepository.saveAll(terminees);
        }
    }
}
