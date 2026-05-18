package com.example.backend.dto;

import com.example.backend.enums.StatutProjet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjetDTO {

    private Long id;

    @NotBlank(message = "Le nom du projet est obligatoire")
    private String nom;

    @NotNull(message = "La date de debut est obligatoire")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @NotNull(message = "Le budget est obligatoire")
    @Positive(message = "Le budget doit etre positif")
    private Double budget;

    @NotNull(message = "Le statut est obligatoire")
    private StatutProjet statut;

    private Double coutTotal;
    private Double budgetRestant;
    private Integer nombreTaches;
    
    private List<Long> ressourceIds;
    private List<TacheDTO> taches;
}