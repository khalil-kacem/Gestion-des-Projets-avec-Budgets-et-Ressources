package com.example.backend.controller;

import com.example.backend.dto.EmployeDTO;
import com.example.backend.service.EmployeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping
    @Operation(summary = "Liste tous les employés")
    public ResponseEntity<List<EmployeDTO>> getAllEmployes() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un employé par ID")
    public ResponseEntity<EmployeDTO> getEmployeById(@PathVariable Long id) {
        return employeService.getEmployeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crée un nouvel employé")
    public ResponseEntity<EmployeDTO> createEmploye(@Valid @RequestBody EmployeDTO employeDTO) {
        try {
            EmployeDTO created = employeService.createEmploye(employeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un employé")
    public ResponseEntity<EmployeDTO> updateEmploye(@PathVariable Long id,
                                                    @Valid @RequestBody EmployeDTO employeDTO) {
        return employeService.updateEmploye(id, employeDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un employé")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        boolean deleted = employeService.deleteEmploye(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}