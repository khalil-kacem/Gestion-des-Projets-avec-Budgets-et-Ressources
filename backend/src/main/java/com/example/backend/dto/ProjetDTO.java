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
    private String nom;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Double budget;

    private StatutProjet statut;

    private Double coutTotal;
    private Double budgetRestant;
    private Integer nombreTaches;
    
    private List<Long> ressourceIds;
    private List<TacheDTO> taches;
}