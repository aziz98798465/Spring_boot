
package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.dto.VehiculeMapper;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    public VehiculeController(IVehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    public List<VehiculeDTO> afficherVehicules() {
        return vehiculeService.afficherTous()
                .stream()
                .map(VehiculeMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> afficherVehiculeParId(
            @PathVariable Long id) {

        return vehiculeService.afficherParId(id)
                .map(VehiculeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles")
    public Page<VehiculeDTO> afficherDisponibles(Pageable pageable) {
        return vehiculeService.rechercherDisponibles(pageable)
                .map(VehiculeMapper::toDTO);
    }

    @GetMapping("/recherche")
    public Page<VehiculeDTO> rechercher(
            @RequestParam String ville,
            @RequestParam tn.esprit.autoloc.domain.CategorieVehicule categorie,
            Pageable pageable) {
        return vehiculeService.rechercherParVilleEtCategorie(
                        ville, categorie, pageable)
                .map(VehiculeMapper::toDTO);
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> ajouterVehicule(
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        Vehicule vehicule = VehiculeMapper.toEntity(vehiculeDTO);
        vehicule.setIdVehicule(null);

        Vehicule nouveauVehicule = vehiculeService.ajouter(vehicule);
        VehiculeDTO dto = VehiculeMapper.toDTO(nouveauVehicule);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> modifierVehicule(
            @PathVariable Long id,
            @Valid @RequestBody VehiculeDTO vehiculeDTO) {

        try {
            Vehicule vehicule = VehiculeMapper.toEntity(vehiculeDTO);
            Vehicule modifie = vehiculeService.modifier(id, vehicule);
            return ResponseEntity.ok(VehiculeMapper.toDTO(modifie));
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVehicule(
            @PathVariable Long id) {

        try {
            vehiculeService.supprimer(id);
        } catch (java.util.NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
