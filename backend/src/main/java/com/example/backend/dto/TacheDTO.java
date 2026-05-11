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

    private String description;

    private EtatTache etat;

    private PrioriteTache priorite;

    private LocalDate deadline;

    private Long projetId;

    private Long responsableId;
    private String responsableNom;
    
    private List<Long> ressourceIds;
    private List<RessourceDTO> ressources;
}