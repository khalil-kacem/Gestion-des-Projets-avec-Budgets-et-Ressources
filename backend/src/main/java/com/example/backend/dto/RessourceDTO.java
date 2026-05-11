package com.example.backend.dto;

import com.example.backend.enums.DisponibiliteRessource;
import com.example.backend.enums.TypeRessource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RessourceDTO {

    private Long id;

    private String nom;

    private TypeRessource type;

    private Double cout;

    private DisponibiliteRessource disponibilite;
}