package com.example.backend.dto;

import lombok.Data;

@Data
public class RapportFinancierDTO {

    private Long projetId;
    private String projetNom;
    private Double budgetInitial;
    private Double coutTotalRessources;
    private Double coutTotalTaches;
    private Double budgetRestant;
    private Double pourcentageUtilisation;
    private Integer nombreTaches;
    private Integer nombreTachesTerminees;
}