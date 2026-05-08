package com.example.backend.dto;

import com.example.backend.enums.StatutProjet;
import lombok.Data;

import java.time.LocalDate;

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
}