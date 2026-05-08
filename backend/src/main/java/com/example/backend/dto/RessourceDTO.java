package com.example.backend.dto;

import com.example.backend.enums.TypeRessource;
import lombok.Data;

@Data
public class RessourceDTO {

    private Long id;
    private String nom;
    private TypeRessource type;
    private Double cout;
    private Boolean disponibilite;
}