package tn.esprit.autoloc.service.tarification;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.service.VehiculeValidator;

import java.util.Map;

@Service
public class TarificationService {

    private final Map<String, StrategieTarification> strategies;
    private final VehiculeValidator vehiculeValidator;

    public TarificationService(
            Map<String, StrategieTarification> strategies,
            VehiculeValidator vehiculeValidator) {
        this.strategies = strategies;
        this.vehiculeValidator = vehiculeValidator;
    }

    public double calculer(String type, double tarifJournalier, int jours) {
        if (tarifJournalier <= 0) {
            throw new IllegalArgumentException("Le tarif doit être positif");
        }
        vehiculeValidator.validerNombreJours(jours);

        StrategieTarification strategie = strategies.get(type);
        if (strategie == null) {
            throw new IllegalArgumentException(
                    "Type de tarification inconnu : " + type);
        }

        return strategie.calculer(tarifJournalier, jours);
    }
}
