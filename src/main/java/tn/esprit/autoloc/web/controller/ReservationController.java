
package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Afficher toutes les réservations
    @GetMapping
    public List<ReservationDTO> afficherReservations() {
        return reservationService.afficherReservations();
    }

    // Afficher une réservation par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> afficherReservationParId(
            @PathVariable Long id) {

        return reservationService.afficherReservationParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une réservation avec validation
    @PostMapping
    public ResponseEntity<ReservationDTO> ajouterReservation(
            @Valid @RequestBody ReservationDTO reservationDTO) {

        ReservationDTO nouvelleReservation =
                reservationService.ajouterReservation(reservationDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nouvelleReservation);
    }

    // Modifier une réservation avec validation
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> modifierReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {

        try {
            ReservationDTO modifiee =
                    reservationService.modifierReservation(id, reservationDTO);

            return ResponseEntity.ok(modifiee);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerReservation(
            @PathVariable Long id) {

        try {
            reservationService.supprimerReservation(id);

            return ResponseEntity.noContent().build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
