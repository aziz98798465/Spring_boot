
package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.ClientRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client ajouterClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> afficherClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> afficherClientParId(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client modifierClient(Long id, Client client) {
        Client existant = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Client introuvable : " + id));

        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());

        return clientRepository.save(existant);
    }

    @Override
    public void supprimerClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException(
                    "Client introuvable : " + id);
        }

        clientRepository.deleteById(id);
    }
}
