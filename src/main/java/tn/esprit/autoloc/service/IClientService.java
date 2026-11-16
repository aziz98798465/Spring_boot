
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    Client ajouterClient(Client client);

    List<Client> afficherClients();

    Optional<Client> afficherClientParId(Long id);

    Client modifierClient(Long id, Client client);

    void supprimerClient(Long id);
}
