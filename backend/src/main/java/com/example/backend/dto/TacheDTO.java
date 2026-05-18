package com.example.backend.dto;

import com.example.backend.enums.EtatTache;
import com.example.backend.enums.PrioriteTache;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TacheDTO {

    private Long id;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "L'etat est obligatoire")
    private EtatTache etat;

    @NotNull(message = "La priorite est obligatoire")
    private PrioriteTache priorite;

    private LocalDate deadline;

    @NotNull(message = "Le projet est obligatoire")
    private Long projetId;

    private Long responsableId;
    private String responsableNom;
    
    private List<Long> ressourceIds;
    private List<RessourceDTO> ressources;
}