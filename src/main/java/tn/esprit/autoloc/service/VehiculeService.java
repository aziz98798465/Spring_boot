package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class VehiculeService implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeValidator vehiculeValidator;
    private final JournalisationService journalisationService;

    public VehiculeService(
            VehiculeRepository vehiculeRepository,
            VehiculeValidator vehiculeValidator,
            JournalisationService journalisationService) {
        this.vehiculeRepository = vehiculeRepository;
        this.vehiculeValidator = vehiculeValidator;
        this.journalisationService = journalisationService;
    }

    @Override
    public Vehicule ajouter(Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        journalisationService.journaliser("Ajout du véhicule");
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> afficherTous() {
        return vehiculeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> afficherParId(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    public Vehicule modifier(Long id, Vehicule vehicule) {
        vehiculeValidator.valider(vehicule);
        Vehicule existant = vehiculeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Véhicule introuvable : " + id));

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        journalisationService.journaliser("Modification du véhicule " + id);
        return vehiculeRepository.save(existant);
    }

    @Override
    public void supprimer(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new NoSuchElementException("Véhicule introuvable : " + id);
        }

        journalisationService.journaliser("Suppression du véhicule " + id);
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculerTarif(Vehicule vehicule, int nombreJours) {
        vehiculeValidator.valider(vehicule);
        vehiculeValidator.validerNombreJours(nombreJours);
        return vehicule.getTarifJournalier().doubleValue() * nombreJours;
    }
}
