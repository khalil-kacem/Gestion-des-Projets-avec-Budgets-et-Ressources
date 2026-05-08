package com.example.backend.dto;

import com.example.backend.enums.EtatTache;
import com.example.backend.enums.PrioriteTache;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TacheDTO {

    private Long id;
    private Long projetId;
    private Long responsableId;
    private String responsableNom;
    private String description;
    private EtatTache etat;
    private PrioriteTache priorite;
    private LocalDate deadline;
    private Double coutTotal;
}