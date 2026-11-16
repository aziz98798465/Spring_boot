
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    // Afficher tous les clients
    @GetMapping
    public List<Client> afficherClients() {
        return clientService.afficherClients();
    }

    // Afficher un client par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> afficherClientParId(
            @PathVariable Long id) {

        return clientService.afficherClientParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter un client avec validation
    @PostMapping
    public ResponseEntity<Client> ajouterClient(
            @Valid @RequestBody Client client) {

        Client nouveauClient = clientService.ajouterClient(client);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nouveauClient);
    }

    // Modifier un client avec validation
    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(
            @PathVariable Long id,
            @Valid @RequestBody Client client) {

        try {
            Client clientModifie =
                    clientService.modifierClient(id, client);

            return ResponseEntity.ok(clientModifie);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(
            @PathVariable Long id) {

        try {
            clientService.supprimerClient(id);

            return ResponseEntity.noContent().build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
