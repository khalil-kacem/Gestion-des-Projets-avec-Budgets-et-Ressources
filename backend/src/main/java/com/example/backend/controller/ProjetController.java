package com.example.backend.controller;

import com.example.backend.dto.ProjetDTO;
import com.example.backend.dto.RapportFinancierDTO;
import com.example.backend.service.ProjetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*")
@Tag(name = "Projets", description = "Gestion des projets")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    @GetMapping
    @Operation(summary = "Liste tous les projets")
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        return ResponseEntity.ok(projetService.getAllProjets());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un projet par son ID")
    public ResponseEntity<ProjetDTO> getProjetById(@PathVariable Long id) {
        return projetService.getProjetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crée un nouveau projet")
    public ResponseEntity<ProjetDTO> createProjet(@Valid @RequestBody ProjetDTO projetDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projetService.createProjet(projetDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un projet")
    public ResponseEntity<ProjetDTO> updateProjet(@PathVariable Long id,
                                                   @Valid @RequestBody ProjetDTO projetDTO) {
        return projetService.updateProjet(id, projetDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un projet")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        boolean deleted = projetService.deleteProjet(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{projetId}/ressources/{ressourceId}")
    @Operation(summary = "Retire une ressource d'un projet")
    public ResponseEntity<Void> retirerRessource(@PathVariable Long projetId, @PathVariable Long ressourceId) {
        boolean removed = projetService.retirerRessource(projetId, ressourceId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/ressources")
    @Operation(summary = "Assigne des ressources à un projet")
    public ResponseEntity<ProjetDTO> assignerRessources(@PathVariable Long id,
                                                         @RequestBody List<Long> ressourceIds) {
        return projetService.assignerRessources(id, ressourceIds)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/rapport")
    @Operation(summary = "Génère le rapport financier d'un projet")
    public ResponseEntity<RapportFinancierDTO> getRapportFinancier(@PathVariable Long id) {
        return projetService.genererRapportFinancier(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all-with-ressources")
    @Operation(summary = "Liste toutes les associations projet-ressource avec détails")
    public ResponseEntity<List<Map<String, Object>>> getAllWithRessources() {
        List<Map<String, Object>> result = projetService.getAllWithRessources();
        return ResponseEntity.ok(result);
    }
}