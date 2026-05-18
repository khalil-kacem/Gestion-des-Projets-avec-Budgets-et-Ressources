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

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le type est obligatoire")
    private TypeRessource type;

    @NotNull(message = "Le cout est obligatoire")
    @PositiveOrZero(message = "Le cout doit etre positif ou zero")
    private Double cout;

    @NotNull(message = "La disponibilite est obligatoire")
    private DisponibiliteRessource disponibilite;
}