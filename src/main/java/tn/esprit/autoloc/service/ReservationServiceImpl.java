
package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationDTO;
import tn.esprit.autoloc.web.dto.ReservationMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            ClientRepository clientRepository,
            VehiculeRepository vehiculeRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public ReservationDTO ajouterReservation(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        appliquerDTO(reservation, dto);

        Reservation enregistree = reservationRepository.save(reservation);
        return ReservationMapper.toDTO(enregistree);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> afficherReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationDTO> afficherReservationParId(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationMapper::toDTO);
    }

    @Override
    public ReservationDTO modifierReservation(Long id, ReservationDTO dto) {
        Reservation existante = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Réservation introuvable : " + id));

        appliquerDTO(existante, dto);

        Reservation modifiee = reservationRepository.save(existante);
        return ReservationMapper.toDTO(modifiee);
    }

    private void appliquerDTO(Reservation reservation, ReservationDTO dto) {
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());

        if (dto.getIdClient() == null) {
            throw new IllegalArgumentException("L'identifiant du client est obligatoire.");
        }

        if (dto.getIdVehicule() == null) {
            throw new IllegalArgumentException("L'identifiant du véhicule est obligatoire.");
        }

        Client client = clientRepository.findById(dto.getIdClient())
                .orElseThrow(() -> new NoSuchElementException(
                        "Client introuvable : " + dto.getIdClient()));

        Vehicule vehicule = vehiculeRepository.findById(dto.getIdVehicule())
                .orElseThrow(() -> new NoSuchElementException(
                        "Véhicule introuvable : " + dto.getIdVehicule()));

        reservation.setClient(client);
        reservation.setVehicule(vehicule);
    }

    @Override
    public void supprimerReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException(
                    "Réservation introuvable : " + id);
        }

        reservationRepository.deleteById(id);
    }
}
