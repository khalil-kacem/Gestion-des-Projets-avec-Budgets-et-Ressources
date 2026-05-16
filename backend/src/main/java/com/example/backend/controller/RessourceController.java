package com.example.backend.controller;

import com.example.backend.dto.RessourceDTO;
import com.example.backend.service.RessourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ressources")
@CrossOrigin(origins = "*")
@Tag(name = "Ressources", description = "Gestion des ressources")
public class RessourceController {

    @Autowired
    private RessourceService ressourceService;

    @GetMapping
    @Operation(summary = "Liste toutes les ressources")
    public ResponseEntity<List<RessourceDTO>> getAllRessources() {
        return ResponseEntity.ok(ressourceService.getAllRessources());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère une ressource par ID")
    public ResponseEntity<RessourceDTO> getRessourceById(@PathVariable Long id) {
        return ressourceService.getRessourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crée une nouvelle ressource")
    public ResponseEntity<RessourceDTO> createRessource(@Valid @RequestBody RessourceDTO ressourceDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ressourceService.createRessource(ressourceDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour une ressource")
    public ResponseEntity<RessourceDTO> updateRessource(@PathVariable Long id,
                                                           @Valid @RequestBody RessourceDTO ressourceDTO) {
        return ressourceService.updateRessource(id, ressourceDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une ressource")
    public ResponseEntity<Void> deleteRessource(@PathVariable Long id) {
        boolean deleted = ressourceService.deleteRessource(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Recherche des ressources par nom")
    public ResponseEntity<List<RessourceDTO>> searchByNom(@RequestParam String nom) {
        return ResponseEntity.ok(ressourceService.searchByNom(nom));
    }
}