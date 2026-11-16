
package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.StatutReservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le statut de la réservation est obligatoire")
    private StatutReservation statut;

    @NotNull(message = "L'identifiant du client est obligatoire")
    @Positive(message = "L'identifiant du client doit être positif")
    private Long idClient;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    @Positive(message = "L'identifiant du véhicule doit être positif")
    private Long idVehicule;

    public ReservationDTO() {
    }

    public ReservationDTO(
            Long idReservation,
            LocalDate dateDebut,
            LocalDate dateFin,
            StatutReservation statut,
            Long idClient,
            Long idVehicule) {
        this.idReservation = idReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.idClient = idClient;
        this.idVehicule = idVehicule;
    }

    @AssertTrue(message = "La date de fin doit être postérieure à la date de début")
    public boolean isDatesValides() {
        if (dateDebut == null || dateFin == null) {
            return true;
        }
        return dateFin.isAfter(dateDebut);
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Long idVehicule) {
        this.idVehicule = idVehicule;
    }
}
