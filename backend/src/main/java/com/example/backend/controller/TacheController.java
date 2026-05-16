package com.example.backend.controller;

import com.example.backend.dto.TacheDTO;
import com.example.backend.service.TacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "*")
@Tag(name = "Tâches", description = "Gestion des tâches")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    @GetMapping
    @Operation(summary = "Liste toutes les tâches")
    public ResponseEntity<List<TacheDTO>> getAllTaches() {
        return ResponseEntity.ok(tacheService.getAllTaches());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère une tâche par ID")
    public ResponseEntity<TacheDTO> getTacheById(@PathVariable Long id) {
        return tacheService.getTacheById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projet/{projetId}")
    @Operation(summary = "Liste les tâches d'un projet")
    public ResponseEntity<List<TacheDTO>> getTachesByProjet(@PathVariable Long projetId) {
        return ResponseEntity.ok(tacheService.getTachesByProjet(projetId));
    }

    @PostMapping
    @Operation(summary = "Crée une nouvelle tâche")
    public ResponseEntity<TacheDTO> createTache(@Valid @RequestBody TacheDTO tacheDTO) {
        return tacheService.createTache(tacheDTO)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour une tâche")
    public ResponseEntity<TacheDTO> updateTache(@PathVariable Long id,
                                                 @Valid @RequestBody TacheDTO tacheDTO) {
        return tacheService.updateTache(id, tacheDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une tâche")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        boolean deleted = tacheService.deleteTache(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/ressources")
    @Operation(summary = "Assigne des ressources à une tâche")
    public ResponseEntity<TacheDTO> assignerRessources(@PathVariable Long id,
                                                        @RequestBody List<Long> ressourceIds) {
        return tacheService.assignerRessources(id, ressourceIds)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/etat/{etat}")
    @Operation(summary = "Change l'état d'une tâche")
    public ResponseEntity<TacheDTO> changerEtat(@PathVariable Long id,
                                                 @PathVariable String etat) {
        return tacheService.changerEtat(id, etat)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}